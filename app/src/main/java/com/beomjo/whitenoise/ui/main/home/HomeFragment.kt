package com.beomjo.whitenoise.ui.main.home

import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseFragment
import com.beomjo.whitenoise.databinding.FragmentHomeBinding
import com.beomjo.whitenoise.utilities.ext.getApplicationComponent

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home,
    HomeViewModel::class
) {

    private val viewModel: HomeViewModel by getViewModel()

    override val viewModelProvideOwner: ViewModelStoreOwner
        get() = this
//    activity as viewModelProvideOwner

    override fun inject() {
        getApplicationComponent().homeComponent().create().inject(this)
    }
}