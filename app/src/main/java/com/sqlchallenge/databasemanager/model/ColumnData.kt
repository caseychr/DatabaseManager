package com.sqlchallenge.databasemanager.model

data class ColumnData(
    var name: String,
    var type: String?,
    var isPk: String?,
    var dataList: List<String>?
) {

    /*constructor(name: String?, type: String?, isPk: String?, columnData: HashMap<String, List<String>>)
            : this(name, type, isPk) {
        dataMap = columnData
    }*/

    override fun toString(): String {
        return "ColumnData(name='$name', type=$type, isPk=$isPk, dataMap=$dataList)"
    }
}