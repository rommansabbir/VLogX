package com.rommansabbir.vlogx.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.vlogx.databinding.ItemSimpleTextBinding

internal class LoggerXAdapter : RecyclerView.Adapter<LoggerXViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun addData(data: String) {
        val temp = differ.currentList.toMutableList()
        temp.add(data)
        differ.submitList(temp)
    }

    fun isEmpty() = differ.currentList.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoggerXViewHolder {
        val binding = ItemSimpleTextBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return LoggerXViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: LoggerXViewHolder, position: Int) {
        holder.bindView(differ.currentList[position])
    }
}

internal class LoggerXViewHolder(private val binding: ItemSimpleTextBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindView(data: String) {
        binding.textView.text = data
    }
}
