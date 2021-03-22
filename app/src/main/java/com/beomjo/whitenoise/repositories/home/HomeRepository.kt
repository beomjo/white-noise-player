package com.beomjo.whitenoise.repositories.home

import com.beomjo.whitenoise.model.Category

interface HomeRepository {

    suspend fun getHomeCategoryList(): List<Category>

}