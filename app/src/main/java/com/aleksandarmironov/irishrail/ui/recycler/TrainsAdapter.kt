package com.aleksandarmironov.irishrail.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aleksandarmironov.irishrail.R
import com.aleksandarmironov.irishrail.databinding.TrainInfoCellBinding
import com.aleksandarmironov.irishrail.pojos.DisplayTrainData

class TrainsAdapter : ListAdapter<DisplayTrainData,
        RecyclerView.ViewHolder>(TrainsAdapterDiffCallback())  {

    private val mDiffer: AsyncListDiffer<DisplayTrainData> = AsyncListDiffer(this,
        TrainsAdapterDiffCallback()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(
            parent
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = mDiffer.currentList[position]
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    fun updateList(data: List<DisplayTrainData>){
        mDiffer.submitList(data)
        notifyDataSetChanged()
    }

    class ViewHolder constructor(private val binding: TrainInfoCellBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DisplayTrainData){
            binding.cellTrainArriveText.text =
                String.format(binding.root.context.getString(R.string.arrive_at), item.userArrivalDestination, item.trainArrivalTime)
            binding.cellTrainCodeText.text =
                String.format(binding.root.context.getString(R.string.train_code), item.trainCode)
            binding.cellTrainDepartTimeText.text =
                String.format(binding.root.context.getString(R.string.depart), item.trainDepartTime)
            binding.cellTrainDestinationText.text =
                String.format(binding.root.context.getString(R.string.destination), item.trainDirection)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TrainInfoCellBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(
                    binding
                )
            }
        }
    }
}

