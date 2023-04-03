package com.example.gptbros.utils

import androidx.recyclerview.widget.RecyclerView
import com.example.gptbros.databinding.SummaryDisplayBinding
import com.example.gptbros.model.api.SummaryItem

class SummaryHolder(
    private val binding: SummaryDisplayBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(summaryItem: SummaryItem) {
        binding.itemTextView.text = summaryItem.content
    }
}