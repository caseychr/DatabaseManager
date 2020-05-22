package com.sqlchallenge.databasemanager.persistence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.sqlchallenge.databasemanager.model.Form

@Dao
interface FormDao {

    @Query("SELECT * FROM Form")
    suspend fun getForms(): List<Form>

    @RawQuery
    suspend fun getAllTables(query: SupportSQLiteQuery): List<String>
}