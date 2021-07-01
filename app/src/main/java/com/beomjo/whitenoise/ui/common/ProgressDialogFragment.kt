package com.beomjo.whitenoise.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseDialogFragment
import com.beomjo.whitenoise.databinding.FragmentProgressDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgressDialogFragment : BaseDialogFragment<FragmentProgressDialogBinding>(
    R.layout.fragment_progress_dialog,
) {

    override val viewModelProvideOwner: ViewModelStoreOwner
        get() = this

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