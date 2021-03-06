package com.beomjo.whitenoise.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.beomjo.whitenoise.factory.ViewModelFactory
import com.skydoves.bindables.BindingActivity
import java.lang.IllegalStateException
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val contentLayoutId: Int,
    private vararg var viewModels: KClass<out BaseViewModel>,
) : BindingActivity<T>(contentLayoutId), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelImpl: MutableList<BaseViewModel> = mutableListOf()

    protected inline fun <reified T : BaseViewModel> getViewModel(): Lazy<T> {
        return lazy {
            `access$viewModelImpl`.find { it is T }?.let { it as T }
                ?: kotlin.run { throw IllegalStateException("Can't find [${T::class.java.simpleName}] type ViewModel") }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        createViewModels()
        bindingLifeCycleOwner()
    }

    private fun createViewModels() {
        for (vm in viewModels) {
            viewModelImpl.add(ViewModelProvider(this, viewModelFactory).get(vm.javaObjectType))
        }
    }

    abstract fun inject()

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = this@BaseActivity
        }
    }

    @PublishedApi
    internal val `access$viewModelImpl`: MutableList<BaseViewModel>
        get() = viewModelImpl

}