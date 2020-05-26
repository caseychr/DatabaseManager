package com.sqlchallenge.databasemanager

import androidx.test.core.app.ApplicationProvider
import com.sqlchallenge.databasemanager.viewmodel.TableListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class TableListViewModelTest {

    @Test
    fun testRetrieveTablesSuccess() {
        val classUnderTest = TableListViewModel(ApplicationProvider.getApplicationContext())

        runBlocking {
            val mockList = listOf( "Table1, Table2, Table3")

            classUnderTest.tablesLiveData.postValue(Resource.Success(mockList))

            classUnderTest.getAllTables()

            val emission = classUnderTest.tablesLiveData.value
            Assert.assertEquals(Resource.Success(mockList), emission)
        }
    }

    @Test
    fun testRetrieveTablesError() {
        val classUnderTest = TableListViewModel(ApplicationProvider.getApplicationContext())

        runBlocking {
            val mockListError = Throwable()

            classUnderTest.tablesLiveData.postValue(Resource.Error(mockListError))

            classUnderTest.getAllTables()

            val emission = classUnderTest.tablesLiveData.value
            Assert.assertTrue(emission is Resource.Error)
        }
    }
}