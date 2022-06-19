package com.invisibles.foodies.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.invisibles.foodies.R
import com.invisibles.foodies.models.NavigationTab

class NavigationTabsOnMainActivityAdapter(private var data: ArrayList<NavigationTab>, private val context: Context): RecyclerView.Adapter<NavigationTabsOnMainActivityAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardName: TextView = itemView.findViewById(R.id.cardName)
        var card: CardView = itemView.findViewById(R.id.card)
        var mainCard: ConstraintLayout = itemView.findViewById(R.id.mainCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.navigation_tabs_main_activity_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardName.text = data[position].name
        if (!data[position].selected){
            holder.cardName.setTextColor(Color.BLACK)
            holder.card.setCardBackgroundColor(Color.WHITE)
            val param = holder.mainCard.layoutParams as ViewGroup.MarginLayoutParams
            param.marginStart = context.resources.getDimensionPixelOffset(R.dimen.card_margin_start_ntm)
            if (position == data.count()-1){
                param.marginEnd = context.resources.getDimensionPixelOffset(R.dimen.card_margin_end_ntm)
            }
            holder.mainCard.layoutParams = param
        }
        else{
            holder.cardName.setTextColor(Color.WHITE)
            holder.card.setCardBackgroundColor(context.resources.getColor(R.color.primary))
        }

        holder.itemView.setOnClickListener { onItemClickListener(position) }

    }

    override fun getItemCount(): Int = data.count()

    @SuppressLint("NotifyDataSetChanged")
    private fun onItemClickListener(position: Int){
        var lastSelected = -1
        for (i in 0 until data.count()) {
            if (data[i].selected)
                lastSelected = i
            data[i].selected = i == position
        }
        if (lastSelected != position){
            notifyItemChanged(position)
            notifyItemChanged(lastSelected)
            FoodCardMainActivityAdapter.selectedCategory = data[position].id
        }
        else{
            data[position].selected = false
            notifyItemChanged(position)
            FoodCardMainActivityAdapter.selectedCategory = -1
        }

    }

}