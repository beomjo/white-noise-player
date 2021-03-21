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
import com.beomjo.whitenoise.factory.ViewModelFactory
import com.beomjo.whitenoise.ui.common.ProgressDialogFragment
import com.skydoves.bindables.BindingFragment
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes contentLayoutId: Int,
    private vararg var viewModels: KClass<out BaseViewModel>,
) : BindingFragment<T>(contentLayoutId), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModelImpl: MutableList<BaseViewModel> = mutableListOf()

    protected var progressDialog: ProgressDialogFragment? = null

    abstract val viewModelProvideOwner: ViewModelStoreOwner

    protected inline fun <reified T : BaseViewModel> getViewModel(): Lazy<T> {
        return lazy {
            viewModelImpl.find { it is T }?.let { it as T }
                ?: kotlin.run { throw IllegalStateException("Can't find [${T::class.java.simpleName}] type ViewModel") }
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
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            inject()
            createViewModels()
            bindingLifeCycleOwner()
            observeViewModel()
        }
    }

    abstract fun inject()

    private fun createViewModels() {
        for (vm in viewModels) {
            viewModelImpl.add(
                ViewModelProvider(
                    viewModelProvideOwner,
                    viewModelFactory
                ).get(vm.javaObjectType)
            )
        }
    }

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun observeViewModel() {
        for (vm in viewModelImpl) {
            observeToast(vm)
            observeProgress(vm)
        }
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

