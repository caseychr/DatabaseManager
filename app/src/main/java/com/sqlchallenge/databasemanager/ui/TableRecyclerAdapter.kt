package com.sqlchallenge.databasemanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sqlchallenge.databasemanager.R

class TableRecyclerAdapter(private val tableList: List<String>,
                           private val tableOnClick: TableOnClick)
    : RecyclerView.Adapter<TableRecyclerAdapter.ViewHolder>() {

    interface TableOnClick {
        fun onClick(name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_table, parent, false))
    }

    override fun getItemCount(): Int {
        return tableList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tableTextView.text = tableList[position]
        holder.itemView.setOnClickListener { tableOnClick.onClick(tableList[position]) }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tableTextView: TextView = itemView.findViewById(R.id.tableTextView)
    }
}