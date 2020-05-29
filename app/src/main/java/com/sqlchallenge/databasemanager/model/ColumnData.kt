package com.sqlchallenge.databasemanager.model

data class ColumnData(
    var name: String,
    var type: String?,
    var isPk: String?,
    var dataList: MutableList<String>?
) {

    override fun toString(): String {
        return "ColumnData(name='$name', type=$type, isPk=$isPk, dataList=$dataList)"
    }
}