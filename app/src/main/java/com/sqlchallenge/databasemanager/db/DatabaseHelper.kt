package com.sqlchallenge.databasemanager.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.*

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private var mDataBase: SQLiteDatabase? = null
    private val mContext: Context
    private var mNeedUpdate = false

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile(INPUT_FILE)
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
                mIOException.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun copyDBFile(inputFile: File) {
        val mInput = FileInputStream(inputFile)
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength = 0
        while (mInput.read(mBuffer).also({ mLength = it }) > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
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
        lateinit var INPUT_FILE: File
    }

    init {
        DB_PATH = context.getApplicationInfo().dataDir.toString() + "/databases/"
        mContext = context
        copyDataBase()
        this.readableDatabase
    }
}