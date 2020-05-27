package com.sqlchallenge.databasemanager.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.sqlchallenge.databasemanager.Resource
import com.sqlchallenge.databasemanager.model.ColumnData

class RowListViewModel(application: Application): BaseViewModel(application){

    val columnsLiveData = MutableLiveData<Resource<List<ColumnData>>>()
    val rowCountLiveData = MutableLiveData<Resource<Int>>()
    val tableDataLiveData = MutableLiveData<Resource<List<String>>>()
    val tableDataLiveDataTest = MutableLiveData<Resource<List<ColumnData>>>()

    fun getColumns(tableName: String) {
        columnsLiveData.loadResource { repo.getColumns(tableName) }
    }

    fun getRowCount(tableName: String) {
        rowCountLiveData.loadResource { repo.getRowCount(tableName) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTableData(col: List<ColumnData>, tableName: String) {
        tableDataLiveDataTest.loadResource { repo.getTableData(col, tableName) }
    }
}
