package com.sqlchallenge.databasemanager.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.sqlchallenge.databasemanager.Resource
import com.sqlchallenge.databasemanager.model.Form
import com.sqlchallenge.databasemanager.persistence.FormDao

class SQLRepository(private val formDao: FormDao) {

    suspend fun getForms(): Resource<List<Form>> {
        return loadApiResource { formDao.getForms() }
    }

    suspend fun getAllTables(): Resource<List<String>> {
        val query = SimpleSQLiteQuery("SELECT name FROM sqlite_master WHERE type='table'")
        return loadApiResource { formDao.getAllTables(query) }
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