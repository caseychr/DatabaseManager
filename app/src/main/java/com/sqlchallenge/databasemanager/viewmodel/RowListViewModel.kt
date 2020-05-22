package com.sqlchallenge.databasemanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sqlchallenge.databasemanager.Resource
import com.sqlchallenge.databasemanager.model.Form
import com.sqlchallenge.databasemanager.persistence.SQLManagerDatabase
import com.sqlchallenge.databasemanager.repository.SQLRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RowListViewModel(application: Application): AndroidViewModel(application) {

    var repo: SQLRepository =
        SQLRepository(SQLManagerDatabase.invoke(application.applicationContext).getFormDao())

    val formsLiveData = MutableLiveData<Resource<List<Form>>>()
    val tablesLiveData = MutableLiveData<Resource<List<String>>>()

    fun getForms() {
        CoroutineScope(Job() + Dispatchers.Main).launch {
            formsLiveData.value = repo.getForms()
        }
    }

    fun getAllTables() {
        CoroutineScope(Job() + Dispatchers.Main).launch {
            tablesLiveData.value = repo.getAllTables()
        }
    }
}

/*
class RowListViewModel(application: Application, private val repository: SQLRepository =
        SQLRepository(SQLManagerDatabase.invoke(application.applicationContext).getFormDao())
): AndroidViewModel(application) {

    val formsLiveData = MutableLiveData<Resource<List<Form>>>()

    fun getForms() {
        CoroutineScope(Job() + Dispatchers.Main).launch {
            formsLiveData.value = repository.getForms()
        }
    }
}*/
