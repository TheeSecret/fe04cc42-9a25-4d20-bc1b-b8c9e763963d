package com.glucode.about_you

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.glucode.about_you.mockdata.MockData


class MainActivity : AppCompatActivity() {

    val viewModel: EngineersViewModel = EngineersViewModel()
    var selectedEngineer : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragment_host)
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_host).navigateUp() || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && data != null) {

            when (requestCode) {
                0 -> {
                    val imageSelected = data.extras!!["data"] as Bitmap?
                    val imageBitMap = imageSelected

                    viewModel.updateEngineerImage(selectedEngineer, imageBitMap)

                }

                1 -> {
                    val imageSelected = data.data
                    val pathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (imageSelected != null) {
                        val myCursor = this.contentResolver.query(
                            imageSelected,
                            pathColumn, null, null, null
                        )
                        if (myCursor != null) {
                            myCursor.moveToFirst()
                            val columnIndex = myCursor.getColumnIndex(pathColumn[0])
                            val picturePath = myCursor.getString(columnIndex)
                            myCursor.close()
                            val imageBitMap =  BitmapFactory.decodeFile(picturePath)

                            viewModel.updateEngineerImage(selectedEngineer, imageBitMap)

                        }
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)

    }

}

