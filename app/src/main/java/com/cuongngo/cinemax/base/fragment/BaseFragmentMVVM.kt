package com.cuongngo.cinemax.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.cuongngo.cinemax.base.viewmodel.BaseViewModel

abstract class BaseFragmentMVVM<DB: ViewDataBinding, VM: BaseViewModel>: BaseFragment<DB>() {
    abstract val viewModel: VM

    open fun onViewCreatedX(view: View, savedInstanceState: Bundle?){}

    open fun onCreateViewX(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?){}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.onCreate()
        onCreateViewX(inflater, container, savedInstanceState)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedX(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

//    protected fun isUserLoggedIn() : Boolean = AppPreferences.getUserAccessToken().isNotEmpty()

    open fun showLoading(){

    }

    open fun hideLoading(){

    }
}