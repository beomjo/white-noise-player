package com.beomjo.whitenoise.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.model.User
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.repositories.home.HomeRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val homeRepository: HomeRepository,
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun init() {
        loadUserInfo()
        loadHomeCategoryList()
    }

    private fun loadUserInfo() {
        launch { _user.value = authRepository.getUserInfo() }
    }

    private fun loadHomeCategoryList() {
        launch {
            _isLoading.value = true
            _categories.value = homeRepository.getHomeCategoryList()
            _isRefreshing.value = false
            _isLoading.value = false
        }

    }

    fun onRefresh() {
        _isRefreshing.value = true
        loadHomeCategoryList()
    }
}