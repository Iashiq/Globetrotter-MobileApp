package hu.bme.aut.mobweb.edoxam.globetrotter.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.mobweb.edoxam.globetrotter.CountryListActivity
import hu.bme.aut.mobweb.edoxam.globetrotter.data.CountryData
import hu.bme.aut.mobweb.edoxam.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.edoxam.globetrotter.databinding.CountryItemBinding


class CountryAdapter(context: Context,dbCountries: List<CountryData>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    var countryItems = mutableListOf<CountryData>()
    private var context: Context

    init {
        this.context=context
        countryItems.addAll(dbCountries)
    }

    inner class ViewHolder(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryItems.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCountry = countryItems[position]

        holder.binding.tvCountryName.text = currentCountry.name.common
        holder.binding.tvCountryCode.text = currentCountry.cca3
        Glide.with(context).load(currentCountry.flags.png).into(holder.binding.ivFlag)

        holder.binding.btDelete.setOnClickListener{
            deleteCountry(holder.adapterPosition)
        }
    }

    private fun deleteCountry(position: Int) {

        val deleteThread = Thread(Runnable{
            CountryDatabase.getInstance(context).countryDao().deleteCountry(countryItems.get(position))
            (context as CountryListActivity).runOnUiThread {
                countryItems.removeAt(position)
                notifyItemRemoved(position)
            }

        })
        deleteThread.start()

    }

    fun addCountry(country: CountryData) {

        val addCountryThread = Thread(Runnable {
            try {
                CountryDatabase.getInstance(context).countryDao().insertCountry(country)
                (context as CountryListActivity).runOnUiThread {
                    countryItems.add(country)
                    notifyItemInserted(countryItems.lastIndex)
                }
            } catch (e: Exception) {
                Snackbar.make(
                    (context as CountryListActivity).binding.root,
                    e.localizedMessage,
                    Snackbar.LENGTH_LONG
                ).show()
            }

        })
        addCountryThread.start()


    }
}
