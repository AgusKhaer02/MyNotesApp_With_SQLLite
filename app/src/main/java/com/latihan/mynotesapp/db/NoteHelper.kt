package com.latihan.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.latihan.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.latihan.mynotesapp.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

class NoteHelper(context : Context) {

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: NoteHelper? = null

        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }

    }

    @Throws(SQLException::class)
//  Open Connection
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

//  Close Connection
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

//  Load all data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

//  Load data selected by id
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

//  Insert/Create
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

//  Update
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

//  Delete
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}