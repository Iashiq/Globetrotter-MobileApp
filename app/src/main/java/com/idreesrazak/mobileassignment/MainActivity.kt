package com.idreesrazak.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.idreesrazak.mobileassignment.adapter.ItemAdapter
import com.idreesrazak.mobileassignment.data.Item
import com.idreesrazak.mobileassignment.data.ItemDatabase
import com.idreesrazak.mobileassignment.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemDialog.ItemHandler{

    private lateinit var binding: ActivityMainBinding
    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        Thread{
            var itemList = ItemDatabase.getDatabase(this).itemDao().readAllData()

            runOnUiThread{
                itemAdapter = ItemAdapter(this, itemList)
                recyclerItem.adapter = itemAdapter
            }
        }.start()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_additem)
        {
            var intentAddItem = Intent()
            intentAddItem.setClass(this,
                ItemDialog::class.java)

            startActivity(intentAddItem)

        }
        else if (item.itemId == R.id.action_delete)
        {
            deleteAllItems()
            Toast.makeText(this, "List Deleted", Toast.LENGTH_LONG).show()

        }

        return super.onOptionsItemSelected(item)
    }

    override fun itemCreated(item: Item) {
        Thread{
            item.itemId = ItemDatabase.getDatabase(this).itemDao().insertItem(item)

            runOnUiThread {
                itemAdapter.addItem(item)
            }
        }.start()
    }

    fun deleteAllItems(){
        Thread{
            ItemDatabase.getDatabase(this).itemDao().deleteAllItems()

            runOnUiThread{
                itemAdapter.deleteAllItems()
            }
        }.start()
    }
}