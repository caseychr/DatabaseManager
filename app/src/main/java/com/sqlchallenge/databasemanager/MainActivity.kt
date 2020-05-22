package com.sqlchallenge.databasemanager

import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sqlchallenge.databasemanager.model.Form
import com.sqlchallenge.databasemanager.persistence.SQLManagerDatabase
import com.sqlchallenge.databasemanager.ui.RowListFragment
import com.sqlchallenge.databasemanager.ui.TableListFragment
import com.sqlchallenge.databasemanager.viewmodel.RowListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit var db: SQLManagerDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TableListFragment()).commit()
    }

    override fun onResume() {
        super.onResume()
        //db = SQLManagerDatabase.invoke(this)
        //checkFullDB()
    }

    /*private fun checkFullDB(): Boolean {
        val mCursor: Cursor = db.query("SELECT name FROM sqlite_master WHERE type='table'", null)

        val rowExists: Boolean

        rowExists = if (mCursor.moveToFirst()) {
            // DO SOMETHING WITH CURSOR
            println("TRUE There's stuff here: ${mCursor.columnCount}")
            true
        } else {
            // I AM EMPTY
            println("FALSE Crap: ${mCursor.columnCount}")
            false
        }
        println("${mCursor.columnNames.toString()}")
        printTables()
        return rowExists
    }

    fun printTables() {
        val arrTblNames = ArrayList<String>()
        val c: Cursor = db.query("SELECT * FROM GWT", null)
            //db.query("SELECT name FROM sqlite_master WHERE type='table'", null)

        //println("initCD: ${c.columnCount},  ${c.getColumnName(0)}")
        if (c.moveToFirst()) {
            while (!c.isAfterLast) {
                println("initC: ${c.columnCount},  ${c.getColumnName(0)}")
                arrTblNames.add(c.getString(c.getColumnIndex("EditedByTablet")))
                c.moveToNext()
            }
        }
        println("Tables count: ${arrTblNames.size}")
        println("Tables names: $arrTblNames")
        textView.text = arrTblNames.toString()
    }*/
}
