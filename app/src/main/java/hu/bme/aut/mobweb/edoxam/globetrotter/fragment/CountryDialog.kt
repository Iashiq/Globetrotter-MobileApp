package hu.bme.aut.mobweb.edoxam.globetrotter.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.mobweb.edoxam.globetrotter.data.CountryData
import hu.bme.aut.mobweb.edoxam.globetrotter.data.Flags
import hu.bme.aut.mobweb.edoxam.globetrotter.data.Name
import hu.bme.aut.mobweb.edoxam.globetrotter.databinding.DialogCountryNameBinding


class CountryDialog : DialogFragment() {
    private lateinit var countryHandler: CountryHandler
    private lateinit var etCountryName: EditText

    interface CountryHandler{
      fun countryAdded(countryData: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is CountryHandler) {
            countryHandler = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogCountryNameBinding.inflate(layoutInflater)
        etCountryName = binding.etCountryName

        builder.setTitle("CountryDialog")
        builder.setPositiveButton("Ok") {
                _, _ ->
        }

        builder.setNegativeButton("Cancel") {
                _, _ ->
        }
        builder.setView(binding.root)
        return builder.create()
    }


    override fun onResume() {
    super.onResume()
    val pButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
    pButton.setOnClickListener(){
        if(etCountryName.text.isNotEmpty()){
            countryHandler.countryAdded(etCountryName.text.toString())
        }else{
            etCountryName.error = "Cannot be empty"
        }


    }
   }
}