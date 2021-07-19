package com.beomjo.whitenoise.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.ui.common.ProgressDialogFragment
import com.skydoves.bindables.BindingFragment

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes contentLayoutId: Int,
) : BindingFragment<T>(contentLayoutId), LifecycleOwner {

    protected var progressDialog: ProgressDialogFragment? = null

    abstract val viewModelProvideOwner: ViewModelStoreOwner

    inline fun <reified T : BaseViewModel> getViewModel(): Lazy<T> {
        return lazy {
            ViewModelProvider(viewModelProvideOwner)
                .get(T::class.java)
                .apply { observeViewModel(this) }
        }
    }

    protected fun setStatusBarColor(color: Int): Int {
        var previousColor = 0
        return activity?.let {
            val window = requireActivity().window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            previousColor = window.statusBarColor
            window.statusBarColor = color
            return previousColor
        } ?: kotlin.run { previousColor }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            bindingLifeCycleOwner()
        }
    }

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    fun observeViewModel(viewModel: BaseViewModel) {
        observeToast(viewModel)
        observeProgress(viewModel)
    }

    private fun observeToast(vm: BaseViewModel) {
        vm.toast.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeProgress(vm: BaseViewModel) {
        vm.progress.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { isShow ->
                if (isShow) {
                    ProgressDialogFragment.newInstance()
                        .apply { progressDialog = this }
                        .showSafely(parentFragmentManager)
                } else {
                    progressDialog?.dismiss()
                }
            }
        }
    }
}
