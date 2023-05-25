package com.example.cointwo.ui.home.coininfo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cointwo.databinding.FragmentHomeCoinInfoBinding
import com.example.cointwo.ui.base.BaseFragment
import com.example.cointwo.ui.custom.CoinListItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import repeatCollectOnStarted

@AndroidEntryPoint
class CoinInfoFragment : BaseFragment<FragmentHomeCoinInfoBinding>(FragmentHomeCoinInfoBinding::inflate) {

    private val coinInfoViewModel: CoinInfoFragmentViewModel by viewModels()
    lateinit var coinListAdapter : CoinListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeFlowData()
    }


    private fun initView() {
//            coinInfoViewModel.getTickerList()
        coinListAdapter = CoinListAdapter()
        binding.rvCoinList.adapter = coinListAdapter
        binding.rvCoinList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCoinList.addItemDecoration(CoinListItemDecoration(requireContext()))

    }

    private fun observeFlowData(){
        repeatCollectOnStarted {
            coinInfoViewModel.coinInfo.collect {data ->
                Log.d("dodo55 ", "ittt : ${data}")
                data?.let {
//                    coinListAdapter.upd
                    coinListAdapter.submitList(it.toMutableList())
                }

            }
        }
    }


}