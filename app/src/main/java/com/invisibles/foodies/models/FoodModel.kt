package com.invisibles.foodies.models

data class FoodModel(
    var id: Int,
    var category_id: Int,
    var name: String,
    var description: String,
    var image: String,
    var price_current: Int,
    var price_old: Int,
    var measure: Int,
    var measure_unit: String,
    var energy_per_100_grams: Double,
    var proteins_per_100_grams: Double,
    var fats_per_100_grams: Double,
    var carbohydrates_per_100_grams: Double,
    var tag_ids: ArrayList<Int>
)
