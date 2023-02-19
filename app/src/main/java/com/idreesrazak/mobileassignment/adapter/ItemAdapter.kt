package com.idreesrazak.mobileassignment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import com.idreesrazak.mobileassignment.ItemDetails
import com.idreesrazak.mobileassignment.MainActivity
import com.idreesrazak.mobileassignment.R
import com.idreesrazak.mobileassignment.data.Item
import com.idreesrazak.mobileassignment.data.ItemDatabase
import com.idreesrazak.mobileassignment.databinding.ItemViewBinding

class ItemAdapter(private val context: Context, listOfItems: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {}

    private var items = mutableListOf<Item>()

    init {
        items.addAll(listOfItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        holder.binding.tvName.text = currentItem.name
        //holder.binding.tvDescription.text = currentItem.description
        holder.binding.cbItemStatus.isChecked = currentItem.status
        holder.binding.tvPrice.text = currentItem.price.toString() + " HUF"

        holder.binding.btnDelete.setOnClickListener{
            deleteItem(holder.adapterPosition)
        }
        holder.binding.btnDetails.setOnClickListener {
            var intent = Intent()
            intent.setClass(context,ItemDetails::class.java)
            intent.putExtra("itemId",items[holder.adapterPosition])
            startActivity(context,intent,null)
        }

        holder.binding.btnEdit.setOnClickListener{
            var intent = Intent()
            intent.setClass(context, ItemDetails::class.java)
            intent.putExtra("itemId",items[holder.adapterPosition])
            startActivity(context,intent,null)
        }

        holder.binding.cbItemStatus.setOnClickListener{
            items[holder.adapterPosition].status = holder.binding.cbItemStatus.isChecked
            Thread{
                ItemDatabase.getDatabase(context).itemDao().updateItem(items[holder.adapterPosition])

            }.start()
        }



        if(items[holder.adapterPosition].category == 0){
            holder.binding.ivIcon.setImageResource(R.drawable.ic_food)
        }
        else if(items[holder.adapterPosition].category == 1){
            holder.binding.ivIcon.setImageResource(R.drawable.ic_electronics)
        }
        else if(items[holder.adapterPosition].category == 2){
            holder.binding.ivIcon.setImageResource(R.drawable.ic_books)
        }
        else if(items[holder.adapterPosition].category == 3){
            holder.binding.ivIcon.setImageResource(R.drawable.ic_medicines)
        }
        else if(items[holder.adapterPosition].category == 4){
            holder.binding.ivIcon.setImageResource(R.drawable.ic_makeup)
        }

    }


    fun get(position: Int):Item
    {
        return items[position]
    }

    private fun  deleteItem(position: Int){
        Thread{
            ItemDatabase.getDatabase(context).itemDao().deleteItem(items[position])

            (context as MainActivity).runOnUiThread{
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }

    fun addItem(item: Item)
    {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    fun updateItem(item: Item,editIndex:Int)
    {
        items[editIndex] = item
        notifyItemChanged(editIndex)
    }

    public fun deleteAllItems(){
        items.removeAll(items)
        notifyDataSetChanged()
    }

}