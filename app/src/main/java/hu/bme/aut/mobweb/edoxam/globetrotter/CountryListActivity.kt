package hu.bme.aut.mobweb.edoxam.globetrotter



import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.mobweb.edoxam.globetrotter.adapter.CountryAdapter
import hu.bme.aut.mobweb.edoxam.globetrotter.data.CountryData
import hu.bme.aut.mobweb.edoxam.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.edoxam.globetrotter.databinding.ActivityCountryListBinding
import hu.bme.aut.mobweb.edoxam.globetrotter.fragment.CountryDialog
import hu.bme.aut.mobweb.edoxam.globetrotter.network.NetworkManager
import kotlinx.android.synthetic.main.activity_country_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryListActivity : AppCompatActivity(), CountryDialog.CountryHandler {

    lateinit var binding: ActivityCountryListBinding
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //recyclerCountry.adapter = countryAdapter

        setSupportActionBar(findViewById(R.id.toolbar))

        binding.toolbarLayout.title = title

        binding.fab.setOnClickListener { CountryDialog().show(supportFragmentManager,"Dialog")
        }

        Snackbar.make(binding.root,intent.getStringExtra(MainActivity.KEY_NAME).toString(),Snackbar.LENGTH_LONG).show()

        val thread = Thread(Runnable {
            var dbCountries = CountryDatabase.getInstance(this).countryDao().getAllCountries()

            runOnUiThread {
            countryAdapter = CountryAdapter(this, dbCountries)
            recyclerCountry.adapter = countryAdapter
        }
        })
        thread.start()
    }

    override fun onBackPressed() {
        Snackbar.make(binding.root, "Press Logout", Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_country_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.map )
        {
          Snackbar.make(binding.root, "Map", Snackbar.LENGTH_LONG).show()

        }
        if (item.itemId == R.id.about)
        {
            Snackbar.make(binding.root,getString(R.string.ashiq_muhammad_edoxam_2022), Snackbar.LENGTH_LONG).show()

        }

        if (item.itemId == R.id.logout)
        {
            finish()
            moveTaskToBack(true)

        }

        return super.onOptionsItemSelected(item)
    }

    override fun countryAdded(country: String) {
        NetworkManager.getCountryByName(country).enqueue(object : Callback<List<CountryData>?> {
            override fun onResponse(
                call: Call<List<CountryData>?>,
                response: Response<List<CountryData>?>
            ) {
                if (response.isSuccessful) {
                    countryAdapter.addCountry(response.body()!![0])
                } else {
                    Snackbar.make(binding.root, "Error: " + response.message(),
                        Snackbar.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<CountryData>?>, t: Throwable) {
                t.printStackTrace()
                Snackbar.make(binding.root, "Network request error occured, check LOG",
                    Snackbar.LENGTH_LONG).show()
            }
        })
    }




}