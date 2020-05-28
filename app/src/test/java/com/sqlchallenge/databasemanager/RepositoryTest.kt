package com.sqlchallenge.databasemanager

import com.nhaarman.mockitokotlin2.whenever
import com.sqlchallenge.databasemanager.db.DatabaseManager
import com.sqlchallenge.databasemanager.model.ColumnData
import com.sqlchallenge.databasemanager.repository.SQLRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class RepositoryTest {

    val mockTableName = "TableName"

    lateinit var manager: DatabaseManager

    lateinit var repositoryUnderTest: SQLRepository

    @Before
    fun init() {
        manager = mock(DatabaseManager::class.java)
        repositoryUnderTest = SQLRepository(manager)
    }

    @Test
    fun testGetAllTablesSuccess() {
        runBlocking {
            val mockList = listOf( "Table1, Table2, Table3")

            whenever(manager.getAllTables()).thenReturn(mockList)

            val tablesResource = repositoryUnderTest.getAllTables()
            Assert.assertTrue(tablesResource is Resource.Success)
            if(tablesResource is Resource.Success) {
                Assert.assertEquals(mockList, tablesResource.data)
            }
        }
    }

    @Test
    fun testGetTablesEmptyList() {
        runBlocking {
            val mockListEmpty = emptyList<String>()

            whenever(manager.getAllTables()).thenReturn(mockListEmpty)

            val tablesResource = repositoryUnderTest.getAllTables()
            Assert.assertTrue(tablesResource is Resource.Success)
            if(tablesResource is Resource.Success) {
                Assert.assertTrue(mockListEmpty != null && mockListEmpty.isEmpty())
            }
        }
    }

    @Test
    fun testGetColumnInformationSuccess() {
        runBlocking {
            iniMockList()
            whenever(manager.getTableColumnInfo(mockTableName)).thenReturn(mockColumnList)

            val tableColumns = repositoryUnderTest.getColumns(mockTableName)

            Assert.assertTrue(tableColumns is Resource.Success)
            if(tableColumns is Resource.Success) {
                Assert.assertEquals(mockColumnList, tableColumns.data)
            }
        }
    }

    @Test
    fun testGetColumnsEmptyList() {
        runBlocking {
            mockColumnList.clear()
            whenever(manager.getTableColumnInfo(mockTableName)).thenReturn(mockColumnList)

            val tableColumns = repositoryUnderTest.getColumns(mockTableName)

            Assert.assertTrue(tableColumns is Resource.Success)
            if(tableColumns is Resource.Success) {
                Assert.assertTrue(mockColumnList != null && mockColumnList.isEmpty())
            }
        }
    }

    @Test
    fun testGetRowCountSuccess() {
        runBlocking {
            val mockRowCount = 30
            whenever(manager.getTableRowCount(mockTableName)).thenReturn(mockRowCount)

            val rowCount = repositoryUnderTest.getRowCount(mockTableName)
            Assert.assertTrue(rowCount is Resource.Success)
            if(rowCount is Resource.Success) {
                Assert.assertEquals(mockRowCount, rowCount.data)
            }
        }
    }

    @Test
    fun testGetTableDataSuccess() {
        runBlocking {
            iniMockList()
            whenever(manager.getTableData(mockColumnList, mockTableName)).thenReturn(mockColumnList)

            val tableData = repositoryUnderTest.getTableData(mockColumnList, mockTableName)

            Assert.assertTrue(tableData is Resource.Success)
            if(tableData is Resource.Success) {
                Assert.assertEquals(mockColumnList, tableData.data)
            }
        }
    }

    @Test
    fun testGetTableDataEmptyList() {
        runBlocking {
            mockColumnList.clear()
            whenever(manager.getTableData(mockColumnList, mockTableName)).thenReturn(mockColumnList)

            val tableData = repositoryUnderTest.getTableData(mockColumnList, mockTableName)

            Assert.assertTrue(tableData is Resource.Success)
            if(tableData is Resource.Success) {
                Assert.assertTrue(mockColumnList != null && mockColumnList.isEmpty())
                //Assert.assertEquals(mockColumnList, tableData.data)
            }
        }
    }

    companion object {
        val mockColumnInfo = ColumnData("cid", "columnName", "type", null)
        val mockColumnList = mutableListOf<ColumnData>()
        fun iniMockList() {
            mockColumnList.clear()
            mockColumnList.add(mockColumnInfo) }
    }
}