package com.sqlchallenge.databasemanager.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sqlchallenge.databasemanager.Resource

class TableListViewModel(application: Application) : BaseViewModel(application) {
    val tablesLiveData = MutableLiveData<Resource<List<String>>>()

    fun getAllTables() {
        tablesLiveData.loadResource { repo.getAllTables() }
    }
}
