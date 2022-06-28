package com.example.healthcare.features_news.presentation.fragments.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.databinding.ItemRvBinding
import com.example.healthcare.features_news.data.remote.response.Result

class DashboardAdapter(
    private val context: Context,
    private val list: List<Result>,
    private val onClick:OnItemClickListener
) : RecyclerView.Adapter<DashboardAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result) {
            binding.apply {
                tvMedicineName.text = data.name
                tvDoze.text = data.dose
                tvStrength.text = data.strength
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener { onClick.setOnItemClickListener(list[position]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(data: Result)
    }
}