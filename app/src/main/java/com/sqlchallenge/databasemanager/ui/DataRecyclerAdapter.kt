package com.sqlchallenge.databasemanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sqlchallenge.databasemanager.R
import com.sqlchallenge.databasemanager.model.ColumnData

private const val COLUMN_INFO = 1

class DataRecyclerAdapter(val columnInfoList: List<ColumnData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var valueList: List<String>?
    init {
        valueList = composeValueList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == COLUMN_INFO) {
          ColumnViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_column_info, parent, false))
        } else {
            ValueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_value, parent, false))
        }
    }

    override fun getItemCount(): Int {
        val rows = valueList!!.size / columnInfoList.size
        return if(rows > 0 && rows != null){columnInfoList.size * rows} else {columnInfoList.size}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ColumnViewHolder) {
            holder.columnName.text = columnInfoList[position].name
            holder.columnType.text = columnInfoList[position].type
            holder.keyImage.visibility = showPrimaryKey(columnInfoList[position])
        } else {
            (holder as ValueViewHolder).valueTV.text = valueList?.get(position)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if(columnInfoList[0].dataList !=  null && columnInfoList[0].dataList!!.size > 0) {
            return if(position <= columnInfoList.size-1) {
                COLUMN_INFO
            } else {
                0
            }
        } else {
            return COLUMN_INFO
        }
    }

    class ValueViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var valueTV: TextView = itemView.findViewById(R.id.valueTextView)
    }

    class ColumnViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var columnName: TextView = itemView.findViewById(R.id.columnNameTextView)
        var columnType: TextView = itemView.findViewById(R.id.columnTypeTextView)
        var keyImage: ImageView = itemView.findViewById(R.id.primaryKeyImg)
    }

    private fun showPrimaryKey(columnInfo: ColumnData): Int {
        return if(columnInfo.isPk.equals("1")) {View.VISIBLE} else {View.INVISIBLE}
    }

    private fun composeValueList(): List<String>? {
        val valueList: MutableList<String> = mutableListOf()
        do {
            if(columnInfoList[0].dataList!!.isNotEmpty()) {
                for(element in columnInfoList) {
                    val s = element.dataList?.get(0)
                    element.dataList?.removeAt(0)
                    s?.let { valueList.add(it) }
                }
            }
        } while (columnInfoList[columnInfoList.size-1].dataList!!.isNotEmpty())
        if(columnInfoList[0].dataList!!.isEmpty()) {
            for(list in columnInfoList) {
                list.dataList = mutableListOf()
            }
        }
        return valueList
    }
}