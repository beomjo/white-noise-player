package com.beomjo.whitenoise.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.model.HomeItem
import javax.inject.Inject


class HomeViewModel @Inject constructor() : BaseViewModel() {

    val homeItems = MutableLiveData<List<HomeItem>>()


    init {
        homeItems.value = listOf(
            HomeItem(
                title = "1",
                iconUrl = ""
            ),
            HomeItem(
                title = "2",
                iconUrl = ""
            )
        )
    }
}