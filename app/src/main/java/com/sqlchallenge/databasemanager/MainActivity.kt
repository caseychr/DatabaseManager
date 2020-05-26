package com.sqlchallenge.databasemanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sqlchallenge.databasemanager.ui.RowListFragment
import com.sqlchallenge.databasemanager.ui.TableListFragment
import com.sqlchallenge.databasemanager.ui.UICommunicator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UICommunicator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(TableListFragment())
    }

    override fun onBackPressed() {
        if(supportFragmentManager.findFragmentById(R.id.fragment_container) is RowListFragment) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun displayProgress(isLoading: Boolean) {
        if(isLoading) {
            fragment_container.visibility = View.GONE
            loadingProgressBar.visibility = View.VISIBLE
        } else {
            loadingProgressBar.visibility = View.GONE
            fragment_container.visibility = View.VISIBLE
        }
    }
}

fun AppCompatActivity.loadFragment(fragment: Fragment, addToBackStack: Boolean = false) {
    val fragmentTransaction =  supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, fragment)
    if(addToBackStack) {fragmentTransaction.addToBackStack(null)}
    fragmentTransaction.commit()
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
