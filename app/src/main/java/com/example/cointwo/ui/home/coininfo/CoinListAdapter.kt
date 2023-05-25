package com.example.cointwo.ui.home.coininfo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cointwo.data.model.CoinData
import com.example.cointwo.data.model.CoinSocketResponse
import com.example.cointwo.databinding.ItemCoinInfoBinding

class CoinListAdapter : ListAdapter<CoinData, RecyclerView.ViewHolder>(CoinListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoinViewHolder -> holder.bind(getItem(position), position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

//        return if (position == 0){
//            VIEW_TYPE_HEADER
//        }else{
//            VIEW_TYPE_BODY
//        }
    }

    inner class CoinViewHolder(private val binding: ItemCoinInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CoinData, pos: Int) {
            Log.d("dodo55 ","bind 속 ? pos : ${pos}")
            binding.apply {
                tvCoinKorName.text = data.symbol // 마켓코드 임시
                tvPrice.text = data.decimalCurrentPrice //시가
                tvRate.text = data.changePricePrevDay //전일 대비 등락율
                tvVolume.text = data.formattedVolume //24시간 누적 거래량
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_BODY = 2
    }
}

class CoinListDiffCallback : DiffUtil.ItemCallback<CoinData>() {
    override fun areItemsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
        return oldItem.symbol == newItem.symbol
    }

    override fun areContentsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
        return oldItem == newItem
    }

}