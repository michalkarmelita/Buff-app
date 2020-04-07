package com.michalkarmelita.buffsdk.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michalkarmelita.buffsdk.R
import com.michalkarmelita.buffsdk.viewmodel.BuffAnswer


internal class BuffAdapter : RecyclerView.Adapter<AnswerViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: BuffAnswer)
    }

    private lateinit var listener: OnItemClickListener
    private var items: List<BuffAnswer> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        return AnswerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.buff_answer,
                    parent,
                    false
                ),
            listener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<BuffAnswer>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun attachOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

internal class AnswerViewHolder(
    itemView: View,
    private val listener: BuffAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: BuffAnswer) {
        with(itemView) {
            findViewById<TextView>(R.id.answer_text).text = item.answer
            setOnClickListener { listener.onItemClick(item) }
        }
    }
}