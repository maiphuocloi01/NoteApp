package com.example.noteapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.data.Note
import com.example.noteapp.data.Priority
import com.example.noteapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var dataList = emptyList<Note>()

    class MyViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                titleTv.text = note.title
                desciptionTv.text = note.description
                rowBackground.setOnClickListener {
                    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(note)
                    itemView.findNavController().navigate(action)
                }
                when (note.priority) {
                    Priority.HIGH -> priorityIndicator.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.red
                        )
                    )
                    Priority.MEDIUM -> priorityIndicator.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.yellow
                        )
                    )
                    Priority.LOW -> priorityIndicator.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.green
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun setData(note: List<Note>){
        this.dataList = note
        notifyDataSetChanged()
    }
}