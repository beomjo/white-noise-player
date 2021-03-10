package com.beomjo.whitenoise.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beomjo.compilation.util.LogUtil
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
    val homeCategories: LiveData<List<HomeCategory>>
        get() = _homeCategories

    private val _user = MutableLiveData<User>().apply { value = authRepository.getUserInfo() }
    val user: LiveData<User>
        get() = _user

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    init {
        getItemList()
    }

    private fun getItemList() {
        viewModelScope.launch {
            try {
                LogUtil.d(homeRepository.getHomeCategoryList())
                _homeCategories.value = homeRepository.getHomeCategoryList()
                _isRefreshing.value = false
            } catch (e: Exception) {
            }
        }
    }

    fun onRefresh() {
        getItemList()
        _isRefreshing.value = true
    }
}