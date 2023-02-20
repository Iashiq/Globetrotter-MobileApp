package hu.bme.aut.mobweb.edoxam.globetrotter

import android.content.Intent
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.mobweb.edoxam.globetrotter.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val  KEY_NAME = "KEY_NAME"
        const val KEY_PASSWORD = "KEY_PASSWORD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener()
        {
            binding.etUserName.text.clear()
            binding.etPassword.text.clear()
        }

        binding.btnLogin.setOnClickListener()
        {
            if (binding.etUserName.getText().toString().trim().equals(""))
                {
                   binding.etUserName.setError("Cannot be empty")
                }
            else
            {
                var intent = Intent()
                intent.setClass(this, CountryListActivity::class.java)
                intent.putExtra(KEY_NAME, binding.etUserName.getText().toString())
                intent.putExtra(KEY_PASSWORD, binding.etPassword.getText().toString())

                startActivity(intent)
            }

        }
    }

}