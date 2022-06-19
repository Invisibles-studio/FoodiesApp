package com.invisibles.foodies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.invisibles.foodies.MainActivity
import com.invisibles.foodies.R
import com.invisibles.foodies.adapters.BottomSheetAdapter
import com.invisibles.foodies.adapters.FoodCardMainActivityAdapter
import com.invisibles.foodies.models.NavigationTab

class ModalBottomSheet: BottomSheetDialogFragment() {

    private lateinit var buttonDone: AppCompatButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)
        recyclerView = view.findViewById(R.id.modalElements)
        buttonDone = view.findViewById(R.id.modalButton)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapterModalElements = BottomSheetAdapter(tags)
        recyclerView.adapter = adapterModalElements
        recyclerView.isNestedScrollingEnabled = false

        buttonDone.setOnClickListener {

            FoodCardMainActivityAdapter.selectedTypes = BottomSheetAdapter.selectedArray
            MainActivity.updateFiltersCounter()
            dismiss()

        }

        return view
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        lateinit var recyclerView: RecyclerView
        lateinit var tags: ArrayList<NavigationTab>
    }
}