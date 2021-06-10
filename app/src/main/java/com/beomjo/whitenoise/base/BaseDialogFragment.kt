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
import com.beomjo.whitenoise.factory.ViewModelFactory
import com.skydoves.bindables.BindingDialogFragment
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseDialogFragment<B : ViewDataBinding>(
    @LayoutRes private val contentLayoutId: Int,
    private vararg var viewModels: KClass<out BaseViewModel>,
) : BindingDialogFragment<B>(contentLayoutId), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModelImpl: MutableList<BaseViewModel> = mutableListOf()

    protected inline fun <reified T : BaseViewModel> getViewModel(): Lazy<T> {
        return lazy {
            viewModelImpl.find { it is T }?.let { it as T }
                ?: kotlin.run { throw IllegalStateException("Can't find [${T::class.java.simpleName}] type ViewModel") }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            val entryPoint =
                EntryPointAccessors.fromApplication(
                    this.context.applicationContext,
                    BaseActivity.BaseEntryPoints::class.java
                )
            viewModelFactory = entryPoint.getViewModelFactory()
            createViewModels()
            bindingLifeCycleOwner()
            bindingToast()
        }
    }

    private fun createViewModels() {
        for (vm in viewModels) {
            viewModelImpl.add(ViewModelProvider(this, viewModelFactory).get(vm.javaObjectType))
        }
    }

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun bindingToast() {
        for (vm in viewModelImpl) {
            vm.toast.observe(this) { event ->
                event.getContentIfNotHandled()?.let { msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
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