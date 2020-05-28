package com.sqlchallenge.databasemanager

import androidx.test.core.app.ApplicationProvider
import com.sqlchallenge.databasemanager.model.ColumnData
import com.sqlchallenge.databasemanager.viewmodel.RowListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class RowListViewModelTest {

    val mockTableName = "TableName"

    @Test
    fun testRetrieveColumnsSuccess() {
        val classUnderTest = RowListViewModel(ApplicationProvider.getApplicationContext())

        runBlocking {
            val mockColumnInfo = ColumnData("cid", "columnName", "type", mutableListOf("0", "1", "2", "3"))
            val mockColumnList = mutableListOf<ColumnData>()
            mockColumnList.add(mockColumnInfo)

            classUnderTest.columnsLiveData.postValue(Resource.Success(mockColumnList))

            classUnderTest.getColumns(mockTableName)

            val emission = classUnderTest.columnsLiveData.value
            Assert.assertEquals(Resource.Success(mockColumnList), emission)
        }
    }

    @Test
    fun testRetrieveColumnsError() {
        val classUnderTest = RowListViewModel(ApplicationProvider.getApplicationContext())

        runBlocking {
            val mockListError = Throwable()

            classUnderTest.columnsLiveData.postValue(Resource.Error(mockListError))

            classUnderTest.getColumns(mockTableName)

            val emission = classUnderTest.columnsLiveData.value
            Assert.assertTrue(emission is Resource.Error)
        }
    }

    @Test
    fun testRetrieveRowCountSuccess() {
        val classUnderTest = RowListViewModel(ApplicationProvider.getApplicationContext())

        runBlocking {
            val mockRowCount = 30

            classUnderTest.rowCountLiveData.postValue(Resource.Success(mockRowCount))

            classUnderTest.getRowCount(mockTableName)

            val emission = classUnderTest.rowCountLiveData.value
            Assert.assertEquals(Resource.Success(mockRowCount), emission)
        }
    }

    @Test
    fun testRetrieveRowCountError() {
        val classUnderTest = RowListViewModel(ApplicationProvider.getApplicationContext())

        runBlocking {
            val mockListError = Throwable()

            classUnderTest.rowCountLiveData.postValue(Resource.Error(mockListError))

            classUnderTest.getRowCount(mockTableName)

            val emission = classUnderTest.rowCountLiveData.value
            Assert.assertTrue(emission is Resource.Error)
        }
    }

    @Test
    fun testRetrieveTableDatasSuccess() {
        val classUnderTest = RowListViewModel(ApplicationProvider.getApplicationContext())

        runBlocking {
            val mockColumnInfo = ColumnData("cid", "columnName", "type", mutableListOf("0", "1", "2", "3"))
            val mockColumnList = mutableListOf<ColumnData>()
            mockColumnList.add(mockColumnInfo)
            val mockTableDataList = listOf( "tableData1, tableData2, tableData3")

            classUnderTest.tableDataLiveData.postValue(Resource.Success(mockTableDataList))

            classUnderTest.getTableData(mockColumnList, mockTableName)

            val emission = classUnderTest.tableDataLiveData.value
            Assert.assertEquals(Resource.Success(mockTableDataList), emission)
        }
    }

    @Test
    fun testRetrieveTablDataError() {
        val classUnderTest = RowListViewModel(ApplicationProvider.getApplicationContext())

        runBlocking {
            val mockColumnInfo = ColumnData("cid", "columnName", "type", mutableListOf("0", "1", "2", "3"))
            val mockColumnList = mutableListOf<ColumnData>()
            mockColumnList.add(mockColumnInfo)

            val mockListError = Throwable()

            classUnderTest.tableDataLiveData.postValue(Resource.Error(mockListError))

            classUnderTest.getTableData(mockColumnList, mockTableName)

            val emission = classUnderTest.tableDataLiveData.value
            Assert.assertTrue(emission is Resource.Error)
        }
    }
}