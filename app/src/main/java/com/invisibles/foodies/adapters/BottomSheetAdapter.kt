package com.invisibles.foodies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.invisibles.foodies.R
import com.invisibles.foodies.models.NavigationTab

class BottomSheetAdapter(private val data: ArrayList<NavigationTab>): RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var bottomLine: ImageView = itemView.findViewById(R.id.bottomLine)
        var checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_recycler_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].name
        if (position == data.count()-1)
            holder.bottomLine.visibility = View.GONE

        holder.checkbox.isChecked = selectedArray.contains(data[position].id)
        holder.checkbox.setOnCheckedChangeListener { compoundButton, b -> onCheckBoxClicked(position, b) }
    }

    override fun getItemCount(): Int = data.count()

    fun onCheckBoxClicked(position: Int, state: Boolean){

        if (state) selectedArray.add(data[position].id)
        else selectedArray.remove(data[position].id)

    }

    companion object {
        var selectedArray: ArrayList<Int> = arrayListOf()
    }
}