package com.sqlchallenge.databasemanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sqlchallenge.databasemanager.R

class ValueRecyclerAdapter(private val valueList: List<String>?): RecyclerView.Adapter<ValueRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_value, parent, false))
    }

    override fun getItemCount(): Int {
        return valueList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.valueTV.text = valueList?.get(position).toString()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var valueTV: TextView = itemView.findViewById(R.id.valueTextView)
    }
}