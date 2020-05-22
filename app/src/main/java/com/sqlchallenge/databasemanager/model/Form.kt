package com.sqlchallenge.databasemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(name = "_id_Form", value = ["_id"]), Index(name = "ApiName_Form", value = ["ApiName"])))
data class Form(
    @ColumnInfo(name = "ApiName")
    var ApiName: String?,
    @ColumnInfo(name = "FieldDataTableName")
    var FieldDataTableName: String?,
    @PrimaryKey
    @ColumnInfo(name = "_id")
    var _id: Int?,
    @ColumnInfo(name = "ParentFieldDataTableName")
    var ParentFieldDataTableName: String?,
    @ColumnInfo(name = "TemplateVal")
    var TemplateVal: Int?,
    @ColumnInfo(name = "Title")
    var Title: String?
) {
    override fun toString(): String {
        return "Form(ApiName=$ApiName, FieldDataTableName=$FieldDataTableName, _id=$_id, ParentFieldDataTableName=$ParentFieldDataTableName, TemplateVal=$TemplateVal, Title=$Title)"
    }
}