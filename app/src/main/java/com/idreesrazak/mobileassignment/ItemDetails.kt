package com.idreesrazak.mobileassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.idreesrazak.mobileassignment.Api.RetrofitInstance
import com.idreesrazak.mobileassignment.data.Item
import com.idreesrazak.mobileassignment.databinding.ActivityDetailsBinding
import com.idreesrazak.mobileassignment.model.ExchangeRates
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemDetails : AppCompatActivity() {
    private lateinit var  binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var item: Item = intent.getSerializableExtra("itemId") as Item
        var itemPrice: Int = item.price

        binding.tvNameDetail.setText(item.name)
        binding.tvDescriptionDetail.setText(item.description)

        //Find the category name
        val categories = resources.getStringArray(R.array.categories).toList()
        binding.tvCategoryDetail.setText(categories.get(item.category).toString())

        //Status of the item
        if(item.status)
            binding.tvStatusDetail.setText("Purchased")
        else
            binding.tvStatusDetail.setText("Not purchased yet")

        binding.tvPriceDetail.setText(itemPrice.toString()+" HUF")

        binding.btnGetRates.setOnClickListener {
            //To prevent infinite appending
            binding.tvResult.setText("")
            binding.tvPriceDetail.setText(itemPrice.toString()+" HUF")

            val rateCall = RetrofitInstance
            val moneyApi = rateCall.api.getRates("HUF")

            moneyApi.enqueue(object : Callback<ExchangeRates>
            {
                override fun onFailure(call: Call<ExchangeRates>, t: Throwable)
                {
                    binding.tvResult.setText(t.message)
                }

                override fun onResponse(call: Call<ExchangeRates>, response: Response<ExchangeRates>) {
                    //HUF to EUR
                    var eurRate: Double? = response.body()?.rates?.EUR?.toDouble()
                    var priceInEUR = String.format("%.3f", itemPrice * eurRate!! )

                    binding.tvPriceDetail.append("\n"+priceInEUR+" EUR")
                    binding.tvResult.append("EUR: "+eurRate+"\n")

                    //HUF to CAD
                    var cadRate: Double? = response.body()?.rates?.CAD?.toDouble()
                    var priceInCAD = String.format("%.3f", itemPrice * cadRate!! )

                    binding.tvPriceDetail.append("\n"+priceInCAD+" CAD")
                    binding.tvResult.append("CAD: "+cadRate+"\n")

                    //HUF to JPY
                    var jpyRate: Double? = response.body()?.rates?.JPY?.toDouble()
                    var priceInJPY = String.format("%.3f", itemPrice * jpyRate!! )

                    binding.tvPriceDetail.append("\n"+priceInJPY+" JPY")
                    binding.tvResult.append("JPY: "+jpyRate+"\n")
                }
            })

        }
    }
}