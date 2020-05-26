package com.sqlchallenge.databasemanager.repository

import com.sqlchallenge.databasemanager.Resource
import com.sqlchallenge.databasemanager.db.DatabaseManager
import com.sqlchallenge.databasemanager.model.ColumnInformation

class SQLRepository(private val manager: DatabaseManager) {

    suspend fun getAllTables(): Resource<List<String>> {
        return loadApiResource { manager.getAllTables() }
    }

    suspend fun getColumns(tableName: String): Resource<List<ColumnInformation>> {
        return loadApiResource { manager.getTableColumnInfo(tableName) }
    }

    suspend fun getRowCount(tableName: String): Resource<Int> {
        return loadApiResource { manager.getTableRowCount(tableName) }
    }

    suspend fun getTableData(tableName: String): Resource<String> {
        return loadApiResource { manager.getAllTableData(tableName) }
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