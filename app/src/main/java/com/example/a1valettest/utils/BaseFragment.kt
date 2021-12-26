package com.example.a1valettest.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<DBI : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    private lateinit var _binding: DBI

    val binding: DBI
        get() = _binding

    open fun DBI.initialize(savedInstanceState: Bundle?){}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            layoutResId,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        binding.initialize(savedInstanceState = savedInstanceState)

        return _binding.root
    }
}