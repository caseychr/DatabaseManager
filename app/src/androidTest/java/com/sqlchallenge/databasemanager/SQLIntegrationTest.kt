package com.sqlchallenge.databasemanager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.sqlchallenge.databasemanager.db.DatabaseHelper
import com.sqlchallenge.databasemanager.db.DatabaseManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4ClassRunner::class)
class SQLIntegrationTest {

    lateinit var manager: DatabaseManager

    @Before
    fun init() {
        manager = DatabaseManager(InstrumentationRegistry.getInstrumentation().context)
    }

    @Test
    fun testGetAllTables() {

    }

    fun testGetColumnInformation() {

    }

    fun testGetRowCount() {

    }

    fun testGetTableData() {

    }

    fun testGetBadQuery() {

    }
}