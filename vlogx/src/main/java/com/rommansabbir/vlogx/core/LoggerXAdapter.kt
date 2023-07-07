package com.rommansabbir.vlogx.core

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.vlogx.R
import java.lang.ref.WeakReference

internal class LoggerXAdapter(private val context: WeakReference<Activity>) :
    RecyclerView.Adapter<LoggerXViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun addData(data: String) {
        val temp = differ.currentList.toMutableList()
        temp.add(data)
        differ.submitList(temp)
    }

    fun clear() {
        differ.submitList(mutableListOf())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoggerXViewHolder {
        val binding =
            LayoutInflater.from(context.get()).inflate(R.layout.item_simple_text, parent, false)
        return LoggerXViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: LoggerXViewHolder, position: Int) {
        holder.bindView(differ.currentList[position])
    }
}

internal class LoggerXViewHolder(private val binding: View) :
    RecyclerView.ViewHolder(binding) {
    fun bindView(data: String) {
        binding.findViewById<TextView>(R.id.textView).text = data
    }
}
