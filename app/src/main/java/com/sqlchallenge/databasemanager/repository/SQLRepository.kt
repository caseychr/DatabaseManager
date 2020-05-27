package com.sqlchallenge.databasemanager.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.sqlchallenge.databasemanager.Resource
import com.sqlchallenge.databasemanager.db.DatabaseManager
import com.sqlchallenge.databasemanager.model.ColumnData

class SQLRepository(private val manager: DatabaseManager) {

    suspend fun getAllTables(): Resource<List<String>> {
        return loadApiResource { manager.getAllTables() }
    }

    suspend fun getColumns(tableName: String): Resource<List<ColumnData>> {
        return loadApiResource { manager.getTableColumnInfo(tableName) }
    }

    suspend fun getRowCount(tableName: String): Resource<Int> {
        return loadApiResource { manager.getTableRowCount(tableName) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getTableData(columnData: List<ColumnData>, tableName: String): Resource<List<ColumnData>> {
        return loadApiResource { manager.getTableData(columnData, tableName) }
    }

    suspend fun <T> loadApiResource(loader: suspend() -> T): Resource<T> {
        return try {
            Resource.Success(loader())
        } catch (error: Exception) {
            error.printStackTrace()
            return Resource.Error(error)
        }
    }
}