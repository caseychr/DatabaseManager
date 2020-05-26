package com.sqlchallenge.databasemanager.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import java.io.*
import java.sql.SQLException


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private var mDataBase: SQLiteDatabase? = null
    private val mContext: Context
    private var mNeedUpdate = false

    @Throws(IOException::class)
    fun updateDataBase() {
        if (mNeedUpdate) {
            val dbFile =
                File(DB_PATH + DB_NAME)
            if (dbFile.exists()) dbFile.delete()
            copyDataBase()
            mNeedUpdate = false
        }
    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile()
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    @Throws(IOException::class)
    private fun copyDBFile() {
        val mInput: InputStream = mContext.getAssets().open(DB_NAME)
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int = 0
        while (mInput.read(mBuffer).also({ mLength = it }) > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase(): Boolean {
        mDataBase = SQLiteDatabase.openDatabase(
            DB_PATH + DB_NAME,
            null,
            SQLiteDatabase.CREATE_IF_NECESSARY
        )
        return mDataBase != null
    }

    @Synchronized
    override fun close() {
        if (mDataBase != null) mDataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (newVersion > oldVersion) mNeedUpdate = true
    }

    companion object {
        private const val DB_NAME = "DataForensics.db"
        private var DB_PATH = ""
        private const val DB_VERSION = 8
    }

    fun fetch(db: SQLiteDatabase): Cursor? {
        val columns =
            arrayOf<String>("_id", "ApiName", "Title")
        val cursor: Cursor =
            db.query("Form", null, null, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()

        }
        return cursor
    }

    init {
        DB_PATH =
            if (Build.VERSION.SDK_INT >= 17) context.getApplicationInfo().dataDir.toString() + "/databases/" else "/data/data/" + context.getPackageName()
                .toString() + "/databases/"
        mContext = context
        copyDataBase()
        this.readableDatabase
    }
}