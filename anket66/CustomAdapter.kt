package com.bogazliyan.anket66

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_desgin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]

        holder.textView.text = itemsViewModel.text

        val optionsAdapter = OptionsAdapter(itemsViewModel.cevaplar, itemsViewModel)
        holder.optionsRecyclerView.layoutManager =
            LinearLayoutManager(holder.optionsRecyclerView.context)
        holder.optionsRecyclerView.adapter = optionsAdapter



    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val optionsRecyclerView: RecyclerView = itemView.findViewById(R.id.optionsRecyclerView)
    }
}
