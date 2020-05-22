package com.sqlchallenge.databasemanager.persistence


import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface ColumnDao {

    @RawQuery
    fun getColumnInfo(query: SupportSQLiteQuery): List<String>
}