package com.cuongngo.cinemax.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.cuongngo.cinemax.base.view.BaseView
import com.cuongngo.cinemax.base.view.ProgressDialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseFragment<DB: ViewDataBinding>: Fragment(), KodeinAware, BaseView {
    open lateinit var binding: DB

    override val kodein by kodein()

    private val progressDialog by lazy {
        ProgressDialog(requireContext())
    }
    @LayoutRes
    abstract fun inflateLayout(): Int

    override fun provideLoading() : ProgressDialog = progressDialog

    override fun provideContext(): Context? = requireContext()

    override fun provideRootView(): View? = view

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,inflateLayout(),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        setUpObserver()
    }
}