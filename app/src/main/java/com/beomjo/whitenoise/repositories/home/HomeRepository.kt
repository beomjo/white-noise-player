package com.beomjo.whitenoise.repositories.home

import com.beomjo.whitenoise.model.HomeCategory

interface HomeRepository {

    suspend fun getHomeCategoryList(): List<HomeCategory>

}