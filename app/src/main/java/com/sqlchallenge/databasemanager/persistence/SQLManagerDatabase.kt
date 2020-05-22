package com.sqlchallenge.databasemanager.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sqlchallenge.databasemanager.model.Form


@Database(entities = [Form::class], version = 8, exportSchema = true)
abstract class SQLManagerDatabase: RoomDatabase() {

    abstract fun getFormDao(): FormDao

    companion object {
        private val DATABASE_NAME = "DataForensics.db"

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
        SQLManagerDatabase::class.java, DATABASE_NAME)
            .createFromAsset("database/$DATABASE_NAME")
            .fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?:
        buildDatabase(context).also { instance = it }

        private var instance: SQLManagerDatabase? = null
    }
}