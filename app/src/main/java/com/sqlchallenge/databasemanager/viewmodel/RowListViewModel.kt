package com.sqlchallenge.databasemanager.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sqlchallenge.databasemanager.Resource
import com.sqlchallenge.databasemanager.model.ColumnInformation
import com.sqlchallenge.databasemanager.model.Form

class RowListViewModel(application: Application): BaseViewModel(application){

    val formsLiveData = MutableLiveData<Resource<List<Form>>>()
    val columnsLiveData = MutableLiveData<Resource<List<ColumnInformation>>>()
    val rowCountLiveData = MutableLiveData<Resource<Int>>()
    val tableDataLiveData = MutableLiveData<Resource<Any>>()

    fun getForms() {
        formsLiveData.loadResource { repo.getForms() }
    }

    fun getColumns(tableName: String) {
        columnsLiveData.loadResource { repo.getColumns(tableName) }
    }

    fun getRowCount(tableName: String) {
        rowCountLiveData.loadResource { repo.getRowCount(tableName) }
    }

    fun getTableData(tableName: String) {
        tableDataLiveData.loadResource { repo.getTableData(tableName) }
    }
}
