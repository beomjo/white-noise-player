package com.beomjo.whitenoise.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.model.HomeItem
import com.beomjo.whitenoise.model.User
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.skydoves.bindables.bindingProperty
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _homeItems = MutableLiveData<List<HomeItem>>()
    val homeItems: LiveData<List<HomeItem>>
        get() = _homeItems

    private val _user = MutableLiveData<User>().apply { value = authRepository.getUserInfo() }
    val user: LiveData<User>
        get() = _user

    init {
        _homeItems.value = listOf(
            HomeItem(
                title = "1",
                iconUrl = ""
            ),
            HomeItem(
                title = "2",
                iconUrl = ""
            ),
            HomeItem(
                title = "3",
                iconUrl = ""
            ),
            HomeItem(
                title = "4",
                iconUrl = ""
            ),
            HomeItem(
                title = "5",
                iconUrl = ""
            )
        )
    }
}