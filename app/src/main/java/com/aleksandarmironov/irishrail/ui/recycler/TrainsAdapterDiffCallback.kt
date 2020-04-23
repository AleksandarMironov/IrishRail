package com.aleksandarmironov.irishrail.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import com.aleksandarmironov.irishrail.pojos.DisplayTrainData

class TrainsAdapterDiffCallback : DiffUtil.ItemCallback<DisplayTrainData>() {
    override fun areItemsTheSame(oldItem: DisplayTrainData, newItem: DisplayTrainData): Boolean {
        return oldItem.trainCode == newItem.trainCode
    }

    override fun areContentsTheSame(oldItem: DisplayTrainData, newItem: DisplayTrainData): Boolean {
        return oldItem == newItem
    }
}