package com.example.healthcare.features_news.presentation.fragments.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.databinding.ItemRvBinding
import com.example.healthcare.features_news.data.remote.response.Result

class DashboardAdapter(
    private val list: ArrayList<Result> = ArrayList(),
) : RecyclerView.Adapter<DashboardAdapter.NewsViewHolder>() {

    var onItemClick: ((result: Result) -> Unit)? = null

    inner class NewsViewHolder(private val binding: ItemRvBinding) :
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
        holder.itemView.setOnClickListener { onItemClick?.invoke(list[position]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(data:List<Result>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}