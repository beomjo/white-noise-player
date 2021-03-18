package com.beomjo.whitenoise.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beomjo.compilation.util.Event
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.model.HomeCategory
import com.beomjo.whitenoise.model.User
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.repositories.home.HomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val homeRepository: HomeRepository,
) : BaseViewModel() {

    private val _homeCategories = MutableLiveData<List<HomeCategory>>()
    val homeCategories: LiveData<List<HomeCategory>> get() = _homeCategories

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    fun init() {
        loadUserInfo()
        loadItemList()
    }

    private fun loadUserInfo() {
        launch { _user.value = authRepository.getUserInfo() }
    }

    private fun loadItemList() {
        launch { _homeCategories.value = homeRepository.getHomeCategoryList() }
        _isRefreshing.value = false
    }

    fun onRefresh() {
        _isRefreshing.value = true
    }
}