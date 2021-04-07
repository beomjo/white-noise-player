package com.beomjo.whitenoise.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseFragment
import com.beomjo.whitenoise.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>(
    R.layout.fragment_setting
) {
    override val viewModelProvideOwner: ViewModelStoreOwner
        get() = activity as ViewModelStoreOwner

    override fun inject() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding { fragment = this@SettingFragment }.root
    }

    fun onBackPressed() {
        parentFragmentManager.popBackStack()
    }

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }
}