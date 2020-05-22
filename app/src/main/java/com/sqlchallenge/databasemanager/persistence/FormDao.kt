package com.sqlchallenge.databasemanager.persistence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.sqlchallenge.databasemanager.model.ColumnInformation
import com.sqlchallenge.databasemanager.model.Form

@Dao
interface FormDao {

    @Query("SELECT * FROM Form")
    suspend fun getForms(): List<Form>

    @RawQuery
    suspend fun getAllTables(query: SupportSQLiteQuery): List<String>

    @RawQuery
    suspend fun getColumns(query: SupportSQLiteQuery): List<ColumnInformation>

    @RawQuery
    suspend fun getRowCount(query: SupportSQLiteQuery): Int

    @RawQuery
    suspend fun getTableData(query: SupportSQLiteQuery): Any
}