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
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class ProgressDialogFragment : BaseDialogFragment<FragmentProgressDialogBinding>(
    R.layout.fragment_progress_dialog,
    ProgressDialogViewModel::class
) {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface ProgressDialogEntryPoints {
        fun commonComponent(): CommonComponent.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        this.dialog?.window?.setDimAmount(0.0f)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun inject() {
        activity?.application?.let {
            val entryPoint = EntryPointAccessors.fromApplication(
                it,
                ProgressDialogEntryPoints::class.java
            )
            entryPoint.commonComponent().create()
        } ?: kotlin.run { dismiss() }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProgressDialogFragment()
    }
}