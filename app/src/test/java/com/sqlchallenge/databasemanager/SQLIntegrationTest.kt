package com.sqlchallenge.databasemanager

import com.sqlchallenge.databasemanager.db.DatabaseHelper
import com.sqlchallenge.databasemanager.db.DatabaseManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File

private const val PACKAGE_NAME = "com.sqlchallenge.databasemanager"
private const val FILE_PATH =  "/Users/christophercasey/Android/DatabaseManager/app/src/main/assets/DataForensics.db"
private const val TABLENAME = "Form"

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21], packageName = PACKAGE_NAME)
class SQLIntegrationTest {

    lateinit var manager: DatabaseManager
    lateinit var dbHelper: DatabaseHelper

    @Before
    fun init() {
        DatabaseHelper.INPUT_FILE = File(FILE_PATH)
        dbHelper = DatabaseHelper(RuntimeEnvironment.application)
        manager = DatabaseManager(RuntimeEnvironment.application)
    }

    @Test
    fun testGetAllTables() {
        val list = manager.getAllTables()
        Assert.assertTrue(list.isNotEmpty())
        Assert.assertTrue(list.contains(TABLENAME))
    }

    @Test
    fun testGetColumnInformation() {
        val list = manager.getTableColumnInfo(TABLENAME)
        Assert.assertTrue(list.isNotEmpty())
    }

    @Test
    fun testGetRowCount() {
        val rowCount = manager.getTableRowCount(TABLENAME)
        Assert.assertTrue(rowCount > 0)
    }

    @Test
    fun testGetTableData() {
        val list = manager.getTableColumnInfo(TABLENAME)
        val tableData = manager.getTableData(list,TABLENAME)
        Assert.assertTrue(tableData.isNotEmpty())
    }
}