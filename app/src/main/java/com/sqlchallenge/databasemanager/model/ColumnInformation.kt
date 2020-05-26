package com.sqlchallenge.databasemanager.model

data class ColumnInformation(
    var name: String?,
    var type: String?,
    var isPk: String?
) {
    override fun toString(): String {
        return "ColumnInformation(name=$name, type=$type, isPk=$isPk)"
    }
}