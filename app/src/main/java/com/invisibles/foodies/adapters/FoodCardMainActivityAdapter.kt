package com.invisibles.foodies.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.RecyclerView
import com.invisibles.foodies.R
import com.invisibles.foodies.models.FoodModel

class FoodCardMainActivityAdapter(private var data: ArrayList<FoodModel>, private val context: Context): RecyclerView.Adapter<FoodCardMainActivityAdapter.ViewHolder>() {

    private var backupData: ArrayList<FoodModel> = arrayListOf()

    init {
        controller = this
        backupData = data
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardFoodName: TextView = itemView.findViewById(R.id.cardFoodName)
        val cardFoodWeight: TextView = itemView.findViewById(R.id.cardFoodWeight)
        val blockWithOldPrice: ConstraintLayout = itemView.findViewById(R.id.priceWithOldPrice)
        val blockSinglePrice: ConstraintLayout = itemView.findViewById(R.id.singlePrice)
        val singlePriceText: TextView = itemView.findViewById(R.id.singlePriceText)
        val priceWithOldPriceNew: TextView = itemView.findViewById(R.id.priceWithOldPriceNew)
        val priceWithOldPriceOld: TextView = itemView.findViewById(R.id.priceWithOldPriceOld)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item_main_activity, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.cardFoodName.text = item.name
        holder.cardFoodWeight.text = item.measure.toString() + " " + item.measure_unit

        if (item.price_old != 0){
            holder.blockWithOldPrice.visibility = View.VISIBLE
            holder.blockSinglePrice.visibility = View.GONE
            holder.priceWithOldPriceNew.text = (item.price_current/100).toString() + " ₽"
            holder.priceWithOldPriceOld.text = (item.price_old/100).toString() + " ₽"
            holder.priceWithOldPriceOld.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.blockWithOldPrice.visibility = View.GONE
            holder.blockSinglePrice.visibility = View.VISIBLE
            holder.singlePriceText.text = (item.price_current/100).toString() + " ₽"
        }
    }

    override fun getItemCount(): Int = data.count()

    @SuppressLint("NotifyDataSetChanged")
    private fun updateListWithTag(){
        if (selectedCategory != -1){
            val list = backupData.filter { it.category_id == selectedCategory }
            data = list as ArrayList<FoodModel>
            notifyDataSetChanged()
            if(selectedTypes.count() != 0) updateListTypes()
        }else{
            data = backupData
            if(selectedTypes.count() != 0) updateListTypes()
            notifyDataSetChanged()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateListTypes(){
        if (selectedTypes.count() != 0){
            val list = data.filter { it.tag_ids.containsAll(selectedTypes) }
            data = list as ArrayList<FoodModel>
            Log.d("TEST", selectedTypes.joinToString(" "))
            notifyDataSetChanged()
        }else{
            data = backupData
            if (selectedCategory != -1) updateListWithTag()
            else notifyDataSetChanged()
        }
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var controller: FoodCardMainActivityAdapter

        var selectedCategory: Int = -1
            set(value) {
                field = value
                controller.updateListWithTag()
            }
        var selectedTypes: ArrayList<Int> = arrayListOf()
        set(value) {
            field = value
            controller.updateListTypes()
        }
    }
}