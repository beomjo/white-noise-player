package com.beomjo.whitenoise.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseDialogFragment
import com.beomjo.whitenoise.databinding.FragmentProgressDialogBinding
import com.beomjo.whitenoise.di.common.CommonComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@AndroidEntryPoint
class ProgressDialogFragment : BaseDialogFragment<FragmentProgressDialogBinding>(
    R.layout.fragment_progress_dialog,
    ProgressDialogViewModel::class
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        this.dialog?.window?.setDimAmount(0.0f)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProgressDialogFragment()
    }
}