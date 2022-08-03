package com.example.a7minutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import com.example.a7minutesworkout.databinding.ItemHistoryRecyclerviewBinding

class historyAdaptor(
    private val date : ArrayList<String>
) : RecyclerView.Adapter<historyAdaptor.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvdate =binding.tvDate
        val Main =binding.Main
        val tvId =binding.tvId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val date = date.get(position)
        holder.tvId.text = (position +1).toString()
        holder.tvdate.text= date

        holder.tvdate.text = date.toString()
        if (position % 2 == 0) {
            holder.Main.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.purple_500))
        } else {
            holder.Main.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_700))
        }
    }

    override fun getItemCount(): Int {
        return date.size
    }

}
