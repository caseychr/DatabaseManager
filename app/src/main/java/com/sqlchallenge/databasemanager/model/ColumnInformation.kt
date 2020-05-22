package com.sqlchallenge.databasemanager.model

data class ColumnInformation(

    var cid: String?,
    var name: String?,
    var type: String?,
    var notNull: String?,
    var dflt_value: String?,
    var pk: String?
) {
    override fun toString(): String {
        return "ColumnInformation(cid=$cid, name=$name, type=$type, notNull=$notNull, dflt_value=$dflt_value, pk=$pk)"
    }
}