package com.beomjo.whitenoise.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.factory.ViewModelFactory
import com.beomjo.whitenoise.ui.common.ProgressDialogFragment
import com.skydoves.bindables.BindingDialogFragment
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseDialogFragment<B : ViewDataBinding>(
    @LayoutRes private val contentLayoutId: Int,
) : BindingDialogFragment<B>(contentLayoutId), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    abstract val viewModelProvideOwner: ViewModelStoreOwner

    inline fun <reified T : BaseViewModel> getViewModel(): Lazy<T> {
        return lazy {
            ViewModelProvider(viewModelProvideOwner, viewModelFactory)
                .get(T::class.javaObjectType)
                .apply { observeViewModel(this) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            val entryPoint =
                EntryPointAccessors.fromApplication(
                    this.context.applicationContext,
                    BaseActivity.BaseEntryPoints::class.java
                )
            viewModelFactory = entryPoint.getViewModelFactory()
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
    }

    private fun observeToast(vm: BaseViewModel) {
        vm.toast.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showSafely(manager: FragmentManager): Boolean {
        return if (!manager.isStateSaved) {
            super.show(manager, null)
            true
        } else {
            false
        }
    }
}