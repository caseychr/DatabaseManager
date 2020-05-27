package com.sqlchallenge.databasemanager.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi
import com.sqlchallenge.databasemanager.model.ColumnData

@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class DatabaseManager(context: Context) {

    var database: SQLiteDatabase
    var databaseHelper: DatabaseHelper

    init {
        databaseHelper = DatabaseHelper(context)
        database = databaseHelper.readableDatabase
    }


    fun getAllTables(): List<String> {
        val arrTblNames = ArrayList<String>()
        val c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null)
        if (c.moveToFirst()) {
            while (!c.isAfterLast) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")))
                c.moveToNext()
            }
        }
        return arrTblNames
    }

    fun getTableRowCount(tableName: String): Int {
        val c = database.rawQuery("SELECT * FROM $tableName", null)
        return c.count
    }

    fun getTableColumnInfo(tableName: String): List<ColumnData> {
        val colInfoList = ArrayList<ColumnData>()
        var colInfo: ColumnData? = null
        val cursor = database.rawQuery("SELECT * FROM PRAGMA_TABLE_INFO(\"$tableName\")", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                colInfo = ColumnData(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("pk")), null)
                colInfoList.add(colInfo)
                cursor.moveToNext()
            }
        }
        return colInfoList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTableData(colDataList: List<ColumnData>, tableName: String): List<ColumnData> {
        // make query and start read and store of data values
        val allRows: Cursor = database.rawQuery("SELECT * FROM $tableName", null)
        // get columnName for the data entry
        if(allRows.moveToFirst()) {
            val columnNamesQuery = allRows.columnNames
            do {
                for(name in columnNamesQuery) {
                    val c = colDataList.filter {data -> data.name == name }
                    val i = colDataList.indexOf(c[0])
                    if(colDataList[i].dataList == null) {colDataList[i].dataList = mutableListOf()}
                    (colDataList[i].dataList as MutableList).add(allRows.getString(allRows.getColumnIndex(name)) ?: "NULL")
                }
            } while(allRows.moveToNext())
        } else {
            // Perhaps Empty table
        }
        // find where columnName matches map key and store data value in the List
        return colDataList
    }
}