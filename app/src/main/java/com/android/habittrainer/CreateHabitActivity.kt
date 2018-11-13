package com.android.habittrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import com.android.habittrainer.db.HabitDbTable
import kotlinx.android.synthetic.main.activity_create_habit.*
import java.io.IOException

class CreateHabitActivity : AppCompatActivity() {

    private val CHOOSE_IMAGE_REQUEST = 1
    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
    }

    fun storeHabit(v: View){
        if(et_title.isBlank() || et_descr.isBlank()){
            displayErrorMessage("Your habit needs an enaging title and description")
            return
        }else if (imageBitmap == null){
            displayErrorMessage("Add a motivating picture to your habit")
            return
        }

        val title = et_title.text.toString()
        val description = et_descr.text.toString()
        val habit = Habit(title, description, imageBitmap!!)

        val id = HabitDbTable(this).store(habit)

        if(id == -1L){
            displayErrorMessage("Habit could not be stored")
        }else{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }



    private fun displayErrorMessage(s: String) {
        tv_error.text = s
        tv_error.visibility = View.VISIBLE
    }

    fun chooseImage(v: View){
        val intent = Intent()
        intent.type= "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose image for habit")
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data !=null){
            val bitmap = tryReadBitmap(data.data)
            bitmap?.let{
                this.imageBitmap = bitmap
                iv_image.setImageBitmap(bitmap)
            }
        }
    }

    private fun tryReadBitmap(data: Uri): Bitmap? {
        return try{
            MediaStore.Images.Media.getBitmap(contentResolver,data)

        }catch(e: IOException){
            null
        }
    }
}

private fun EditText.isBlank() = this.text.toString().isBlank()