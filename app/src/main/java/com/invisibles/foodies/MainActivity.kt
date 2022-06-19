package com.invisibles.foodies

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.invisibles.foodies.adapters.BottomSheetAdapter
import com.invisibles.foodies.adapters.FoodCardMainActivityAdapter
import com.invisibles.foodies.adapters.NavigationTabsOnMainActivityAdapter
import com.invisibles.foodies.models.FoodModel
import com.invisibles.foodies.models.NavigationTab
import com.invisibles.foodies.tools.ChangeStatusBarColor
import com.invisibles.foodies.ui.ModalBottomSheet
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var navigationTabs: RecyclerView
    private lateinit var foodRecycler: RecyclerView
    private lateinit var filterButton: ImageView
    private val modalBottomSheet = ModalBottomSheet()
    private lateinit var selectedFilterBlock: ConstraintLayout
    private lateinit var selectedFilterText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        controller = this
        ChangeStatusBarColor(window, resources.getColor(R.color.white))
        navigationTabs = findViewById(R.id.navigation_tabs)
        foodRecycler = findViewById(R.id.food_recycler)
        filterButton = findViewById(R.id.filterButton)
        selectedFilterBlock = findViewById(R.id.selectedFilters)
        selectedFilterText = findViewById(R.id.selectedFiltersText)
        navigationTabs.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        foodRecycler.layoutManager = GridLayoutManager(this, 2)
        val listCategory = getCategories()
        val listFood = getFood()

        val adapterNavigationTab = NavigationTabsOnMainActivityAdapter(listCategory, this)
        val adapterFoodCard = FoodCardMainActivityAdapter(listFood, this)

        navigationTabs.adapter = adapterNavigationTab
        foodRecycler.adapter = adapterFoodCard

        ModalBottomSheet.tags = getTags()

        filterButton.setOnClickListener { modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG) }

    }

    private fun getCategories(): ArrayList<NavigationTab> {
        val text = readFileFromAssets("categories.json")
        val jsonArray = JSONArray(text)
        val list: ArrayList<NavigationTab> = arrayListOf()
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val item = NavigationTab(jsonObject.getInt("id"), jsonObject.getString("name"))
            list.add(item)
        }
        return list
    }

    private fun getFood(): ArrayList<FoodModel>{
        val text = readFileFromAssets("products.json")
        val jsonArray = JSONArray(text)
        val list: ArrayList<FoodModel> = arrayListOf()
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val tagIds = arrayListOf<Int>()
            val tagIdsArray = jsonObject.getJSONArray("tag_ids")
            for (j in 0 until tagIdsArray.length()){
                tagIds.add(tagIdsArray.getInt(j))
            }
            var priceOld = 0
            if (jsonObject.get("price_old").toString() != "null"){
                priceOld = jsonObject.getInt("price_old")
            }
            val item = FoodModel(
                jsonObject.getInt("id"),
                jsonObject.getInt("category_id"),
                jsonObject.getString("name"),
                jsonObject.getString("description"),
                jsonObject.getString("image"),
                jsonObject.getInt("price_current"),
                priceOld,
                jsonObject.getInt("measure"),
                jsonObject.getString("measure_unit"),
                jsonObject.getDouble("energy_per_100_grams"),
                jsonObject.getDouble("proteins_per_100_grams"),
                jsonObject.getDouble("fats_per_100_grams"),
                jsonObject.getDouble("carbohydrates_per_100_grams"),
                tagIds,
            )
            list.add(item)
        }

        return list
    }

    private fun getTags(): ArrayList<NavigationTab>{
        val text = readFileFromAssets("tags.json")
        val jsonArray = JSONArray(text)
        val list: ArrayList<NavigationTab> = arrayListOf()
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val item = NavigationTab(jsonObject.getInt("id"), jsonObject.getString("name"))
            list.add(item)
        }
        return list
    }

    private fun readFileFromAssets(filename: String): String {
        val conf = assets.open(filename)
        val isr = InputStreamReader(conf)
        var text = ""
        var line: String?
        val br = BufferedReader(isr)

        while (true) {
            line = br.readLine()
            if (line == null) break
            text += line + "\n"
        }
        return text
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var controller: MainActivity
        fun updateFiltersCounter(){

            if (BottomSheetAdapter.selectedArray.count() != 0){
                controller.selectedFilterBlock.visibility = View.VISIBLE
                controller.selectedFilterText.text = BottomSheetAdapter.selectedArray.count().toString()
            }else{
                controller.selectedFilterBlock.visibility = View.GONE
            }

        }
    }

}