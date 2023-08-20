package com.bogazliyan.anket66

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView


class OptionsAdapter(
    private val optionsList: List<String>,
    private val itemsViewModel: ItemsViewModel
) : RecyclerView.Adapter<OptionsAdapter.OptionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_option, parent, false)
        return OptionsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class OptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val spinner: Spinner = itemView.findViewById(R.id.spinner)

        fun bind() {
            val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, optionsList)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedOption = parent?.getItemAtPosition(position) as String
                    itemsViewModel.kullaniciCevabi = selectedOption
                    (itemView.context as? MainActivity2)?.onItemModelUpdated(itemsViewModel)


                    // Gerekli işlemleri burada yapabilirsiniz, örneğin kaydetme işlemi vb.
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // İşlem yapılmasını gerektiren bir durum olmadığında
                }
            }
        }
    }
}
