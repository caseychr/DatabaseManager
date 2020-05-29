package com.sqlchallenge.databasemanager

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.sqlchallenge.databasemanager.db.DatabaseHelper
import com.sqlchallenge.databasemanager.ui.RowListFragment
import com.sqlchallenge.databasemanager.ui.TableListFragment
import com.sqlchallenge.databasemanager.ui.UICommunicator
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


const val FILE_SELECT_CODE = 0
class MainActivity : AppCompatActivity(), UICommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyStoragePermissions(this)
        databaseBtn.setOnClickListener {
            if(!databaseBtn.text.contains("Load")) {
                val intent = Intent()
                intent.type = "*/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."), FILE_SELECT_CODE)
            } else {
                databaseBtn.visibility = View.GONE
                loadFragment(TableListFragment())
            }


        }
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

    private fun loadFileUri(uriPath: String?) {
        if(uriPath == null) {Toast.makeText(this@MainActivity.applicationContext,
            "$uriPath is not usable by this app. Please choose something else.", Toast.LENGTH_LONG).show()
        } else {
            val path = uriPath.replace("/document/raw:", "")
            DatabaseHelper.INPUT_FILE = File(path)
            databaseBtn.text = getString(R.string.load_db)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FILE_SELECT_CODE) {
                loadFileUri(data!!.data?.path)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission =
            ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}

fun AppCompatActivity.loadFragment(fragment: Fragment, addToBackStack: Boolean = false) {
    val fragmentTransaction =  supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, fragment)
    if(addToBackStack) {fragmentTransaction.addToBackStack(null)}
    fragmentTransaction.commit()
}
