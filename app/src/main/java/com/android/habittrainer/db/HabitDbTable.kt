package com.android.habittrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.android.habittrainer.Habit
import java.io.ByteArrayOutputStream

class HabitDbTable(context: Context){

    private val dbHelper = HabitTrainerDb(context)

    fun store(habit: Habit): Long{
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(HabitEntry.TITLE_COL,habit.title)
        values.put(HabitEntry.DESCR_COL,habit.description)
        values.put(HabitEntry.IMAGE_COL,toByteArray(habit.image))

        val id = db.transaction {
            insert(HabitEntry.TABLE_NAME, null, values)
        }

        return id
    }

    fun delete(name: String){
        val db = dbHelper.writableDatabase
        db.delete(HabitEntry.TABLE_NAME, "title = ?", arrayOf(name))
    }

    fun readAllHabits(): MutableList<Habit>{
        val columns = arrayOf(HabitEntry._ID,HabitEntry.TITLE_COL,HabitEntry.DESCR_COL,HabitEntry.IMAGE_COL)

        val order = "${HabitEntry._ID} ASC"
        val db = dbHelper.readableDatabase
        val cursor = db.query(HabitEntry.TABLE_NAME, columns, null, null, null, null, order)

        val habits = mutableListOf<Habit>()
        Log.i("HAHA", "TEST1")
        while(cursor.moveToNext()){
            val title = cursor.getString(cursor.getColumnIndex(HabitEntry.TITLE_COL))
            val desc = cursor.getString(cursor.getColumnIndex(HabitEntry.DESCR_COL))
            val byteArray = cursor.getBlob(cursor.getColumnIndex(HabitEntry.IMAGE_COL))
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            habits.add(Habit(title,desc,bitmap))
        }
        Log.i("HAHA", "TEST2")
        cursor.close()

        return habits
    }

    private fun toByteArray(image: Bitmap): ByteArray {

        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG,0,stream)
        return stream.toByteArray()



    }


}


private inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T{
    beginTransaction()
    val result = try{
        val returnValue = function()
        setTransactionSuccessful()
        returnValue
    }finally {
        endTransaction()
    }
    close()
    return result
}