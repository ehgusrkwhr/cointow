package com.example.cointwo.ui.home

import android.os.Bundle
import android.view.View
import com.example.cointwo.R
import com.example.cointwo.databinding.FragmentHomeBinding
import com.example.cointwo.ui.base.BaseFragment
import com.example.cointwo.ui.home.coininfo.CoinInfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        //프레그먼트에 시세표
        val trans = childFragmentManager.beginTransaction()
        trans.replace(binding.container.id, CoinInfoFragment())
        trans.commit()
    }



}