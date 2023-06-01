package com.example.kotlin3laba

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Cartoon.db"
        private const val DATABASE_VERSION = 1
    }

    init {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        if (!dbFile.exists()) {
            val assets = context.assets
            val buffer = ByteArray(1024)
            var length: Int
            val inputStream = assets.open(DATABASE_NAME)
            val outputStream = FileOutputStream(dbFile)
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        }
        else{

        }
    }
    private val dbPath: String = context.getDatabasePath(DATABASE_NAME).path


    override fun getReadableDatabase(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS series")
        onCreate(db);

    }

    fun updateCartoonStatus(idd: Int, status: Int) {
        val values = ContentValues()
        values.put("status", status)

        val db = writableDatabase

        db.beginTransaction()
        try {
            // Выполнение операции обновления данных
            //db.update("series", values, "_id=$idd", arrayOf(idd.toString()))
            db.execSQL("UPDATE series SET status = ${status} WHERE id=${idd}")
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

    }
    @SuppressLint("Range")
    fun getAllFilms(filter:String): List<cartoonInfo> {
        val cartoon = mutableListOf<cartoonInfo>()

        val selectQuery = "SELECT * FROM series ${filter}"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id= cursor.getString(cursor.getColumnIndex("id")).toInt()
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val season = cursor.getString(cursor.getColumnIndex("season"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val image = cursor.getString(cursor.getColumnIndex("image"))
                val status = cursor.getString(cursor.getColumnIndex("status")).toInt()

                val film = cartoonInfo(name, season, description, image, status, id)
                cartoon.add(film)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return cartoon
    }

}