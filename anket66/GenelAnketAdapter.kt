package com.bogazliyan.anket66

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class GenelAnketAdapter(private val mList: List<GenelAnketViewModel>) : RecyclerView.Adapter<GenelAnketAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.genel_anket_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.textView.text = itemsViewModel.isim

        holder.button.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context,MainActivity2::class.java)
            intent.putExtra("bilgi",itemsViewModel.ID)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView8)
        var button : Button = itemView.findViewById(R.id.button4)

        }

    }


