package com.sqlchallenge.databasemanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sqlchallenge.databasemanager.R
import com.sqlchallenge.databasemanager.model.ColumnInformation

class ColumnInfoRecyclerAdapter(
    private val columnInfoList: List<ColumnInformation>): RecyclerView.Adapter<ColumnInfoRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_column_info, parent, false)
        )
    }

    override fun getItemCount(): Int { return columnInfoList.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.columnName.text = columnInfoList[position].name
        holder.columnType.text = columnInfoList[position].type
        holder.keyImage.visibility = showPrimaryKey(columnInfoList[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var columnName: TextView = itemView.findViewById(R.id.columnNameTextView)
        var columnType: TextView = itemView.findViewById(R.id.columnTypeTextView)
        var keyImage: ImageView = itemView.findViewById(R.id.primaryKeyImg)
    }

    private fun showPrimaryKey(columnInfo: ColumnInformation): Int {
        return if(columnInfo.pk.equals("1")) {View.VISIBLE} else {View.GONE}
    }
}