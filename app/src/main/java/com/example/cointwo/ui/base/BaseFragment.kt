package com.example.cointwo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(private val bindingFactory: (LayoutInflater) -> VB) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected lateinit var mContext: Context
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingFactory(inflater)
        mContext = requireContext()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}