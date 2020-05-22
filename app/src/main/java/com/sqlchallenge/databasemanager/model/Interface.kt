package com.sqlchallenge.databasemanager.model

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Interface(
    @PrimaryKey
    @ColumnInfo(name = "FirstFormApiName")
    var firstFormApiName: String
)