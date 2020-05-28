package com.sqlchallenge.databasemanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sqlchallenge.databasemanager.Resource
import com.sqlchallenge.databasemanager.db.DatabaseManager
import com.sqlchallenge.databasemanager.repository.SQLRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val repo: SQLRepository = SQLRepository(DatabaseManager(application))



    fun <T> MutableLiveData<Resource<T>>.loadResource(valueLoader: suspend () -> Resource<T>) {
        this.loadResource(valueLoader, { it })
    }

    fun <T, R> MutableLiveData<R>.loadResource(valueLoader: suspend () -> Resource<T>, mapper: (Resource<T>) -> R) {
        val resourceLiveData = this
        CoroutineScope(Job() + Dispatchers.Main).launch {
            resourceLiveData.value = mapper(Resource.Loading())
            try {
                resourceLiveData.value = mapper(valueLoader())
            } catch (error: Exception) {
                resourceLiveData.value = mapper(Resource.Error(error))
            }
        }
    }
}