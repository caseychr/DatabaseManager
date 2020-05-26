package com.sqlchallenge.databasemanager.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.sqlchallenge.databasemanager.model.ColumnInformation

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

    fun getTableColumnInfo(tableName: String): List<ColumnInformation> {
        val colInfoList = ArrayList<ColumnInformation>()
        var colInfo: ColumnInformation? = null
        val cursor = database.rawQuery("SELECT * FROM PRAGMA_TABLE_INFO(\"$tableName\")", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                colInfo = ColumnInformation(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("pk")))
                colInfoList.add(colInfo)
                cursor.moveToNext()
            }
        }
        return colInfoList
    }

    fun getAllTableData(tableName: String): String {
        Log.d("TAG", "getTableAsString called")
        var tableString = String.format("Table %s:\n", tableName)
        val allRows: Cursor = database.rawQuery("SELECT * FROM $tableName", null)
        if (allRows.moveToFirst()) {
            val columnNames: Array<String> = allRows.getColumnNames()
            do {
                for (name in columnNames) {
                    tableString += java.lang.String.format(
                        "%s: %s\n", name,
                        allRows.getString(allRows.getColumnIndex(name))
                    )
                }
                tableString += "\n"
            } while (allRows.moveToNext())
        }
        return tableString
    }
}