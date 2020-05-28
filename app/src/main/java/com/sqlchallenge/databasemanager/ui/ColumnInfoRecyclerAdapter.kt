package com.sqlchallenge.databasemanager.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sqlchallenge.databasemanager.R
import com.sqlchallenge.databasemanager.model.ColumnData

class ColumnInfoRecyclerAdapter(
    private val columnInfoList: List<ColumnData>): RecyclerView.Adapter<ColumnInfoRecyclerAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_column_info, parent, false)
        )
    }

    override fun getItemCount(): Int { return columnInfoList.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.columnName.text = columnInfoList[position].name
        println("DATA TYPE: ${columnInfoList[position].name}, ${columnInfoList[position].type}")
        holder.columnType.text = columnInfoList[position].type
        holder.keyImage.visibility = showPrimaryKey(columnInfoList[position])

        val valueAdapter = columnInfoList[position].dataList?.let { ValueRecyclerAdapter(it) }
        holder.valueRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = valueAdapter
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var columnName: TextView = itemView.findViewById(R.id.columnNameTextView)
        var columnType: TextView = itemView.findViewById(R.id.columnTypeTextView)
        var keyImage: ImageView = itemView.findViewById(R.id.primaryKeyImg)
        var valueRecyclerView: RecyclerView = itemView.findViewById(R.id.valueRecyclerView)
    }

    private fun showPrimaryKey(columnInfo: ColumnData): Int {
        return if(columnInfo.isPk.equals("1")) {View.VISIBLE} else {View.INVISIBLE}
    }
}