package com.android.habittrainer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HabitTrainerDb(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERISON) {

    private val SQL_CREATE_ENTIRES = "CREATE TABLE ${HabitEntry.TABLE_NAME} (" +
            "${HabitEntry._ID} INTEGER PRIMARY KEY," +
            "${HabitEntry.TITLE_COL} TEXT," +
            "${HabitEntry.DESCR_COL} TEXT," +
            "${HabitEntry.IMAGE_COL} BLOB" +
            ")"


    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${HabitEntry.TABLE_NAME}"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(SQL_CREATE_ENTIRES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(p0)
    }
}