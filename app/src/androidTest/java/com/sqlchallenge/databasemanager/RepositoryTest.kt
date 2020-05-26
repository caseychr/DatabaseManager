package com.sqlchallenge.databasemanager

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.nhaarman.mockitokotlin2.whenever
import com.sqlchallenge.databasemanager.model.ColumnInformation
import com.sqlchallenge.databasemanager.repository.SQLRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoryTest {

    val mockTableName = "TableName"

    lateinit var repositoryUnderTest: SQLRepository

    @Mock
    lateinit var dao: FormDao

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repositoryUnderTest = SQLRepository(dao)
    }

    @Test
    fun testGetAllTablesSuccess() {
        runBlocking {
            val mockList = listOf( "Table1, Table2, Table3")
            val query = SimpleSQLiteQuery("SELECT name FROM sqlite_master WHERE type='table'")
            whenever(dao.getAllTables(query)).thenReturn(mockList)

            val tablesResource = repositoryUnderTest.getAllTables()
            println("DATADATADATA: ${(tablesResource as Resource.Success).data}")
            Assert.assertTrue(tablesResource is Resource.Success)
            //val data = tablesResource as Resource.Success
            //Assert.assertEquals(data.data, mockList)

        }
    }

    @Test
    fun testGetColumnInformationSuccess() {
        runBlocking {
            val query = SimpleSQLiteQuery("PRAGMA table_info($mockTableName);")
            val mockColumnInfo = ColumnInformation("cid", "columnName", "type", "notNull", "0", "0")
            val mockColumnList = mutableListOf<ColumnInformation>()
            mockColumnList.add(mockColumnInfo)
            whenever(dao.getColumns(query)).thenReturn(mockColumnList)

            val tableColumns = repositoryUnderTest.getColumns(mockTableName)
            Assert.assertTrue(tableColumns is Resource.Success)
        }
    }

    @Test
    fun testGetRowCountSuccess() {
        runBlocking {
            val query = SimpleSQLiteQuery("SELECT Count(*) FROM $mockTableName")
            val mockRowCount = 30
            whenever(dao.getRowCount(query)).thenReturn(mockRowCount)

            val rowCount = repositoryUnderTest.getRowCount(mockTableName)
            Assert.assertTrue(rowCount is Resource.Success)
        }
    }

    @Test
    fun testGetTableDataSuccess() {
        runBlocking {
            val tableData = repositoryUnderTest.getTableData(mockTableName)
            Assert.assertTrue(tableData is Resource.Success)
        }
    }

    @Test
    fun testGetBadQuery() {
        runBlocking {

            val tableData = repositoryUnderTest.getTableData(mockTableName)
            Assert.assertTrue(tableData is Resource.Success)
        }
    }
}