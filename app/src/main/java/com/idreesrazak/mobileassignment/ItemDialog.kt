package com.idreesrazak.mobileassignment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.idreesrazak.mobileassignment.data.Item
import com.idreesrazak.mobileassignment.data.ItemDatabase
import com.idreesrazak.mobileassignment.databinding.NewItemBinding
import kotlinx.android.synthetic.main.new_item.*

class ItemDialog : AppCompatActivity() {
    lateinit var itemName: EditText
    lateinit var itemDescription: EditText
    lateinit var itemStatus: CheckBox
    lateinit var categorySpinner: Spinner
    lateinit var itemPrice: EditText


    interface ItemHandler{
        fun itemCreated(item: Item)
    }

    lateinit var itemHandler: ItemHandler
    lateinit var binding: NewItemBinding
    lateinit var context: Context

    companion object
    {
        const val KEY_ADD = "KEY_ADD"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = NewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemName = binding.etItemName
        itemDescription = binding.etItemDescription
        itemPrice = binding.etItemPrice
        itemStatus = binding.cbItemStatus
        categorySpinner = binding.spinnerCategory


        var categoryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )

        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerCategory.adapter = categoryAdapter
    }

    override fun onResume()
    {
        super.onResume()

        binding.toggleButton.setOnClickListener{
            handleNewItemCreate()
        }
    }

    private fun handleNewItemCreate()
    {
        var item = Item(
            null,
            etItemName.text.toString(),
            spinnerCategory.selectedItemPosition.toInt(),
            etItemPrice.text.toString().toInt(),
            cbItemStatus.isChecked,
            etItemDescription.text.toString(),
        )

        Thread{
            ItemDatabase.getDatabase(this).itemDao().insertItem(item)
        }.start()
        var intentMain = Intent(applicationContext, MainActivity::class.java)
        startActivity(intentMain)

    }
}
