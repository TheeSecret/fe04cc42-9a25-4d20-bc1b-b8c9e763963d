package com.glucode.about_you

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView



class ImagePickerHandler ( private val activity: Activity, private val imageView: ImageView)   {

    fun selectImage() {
        // Creating AlertDialog
        val choice = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(activity)
        myAlertDialog.setTitle("Select Image")
        myAlertDialog.setItems(choice, DialogInterface.OnClickListener { dialog, item ->
            when {
                choice[item] == "Choose from Gallery" -> {
                    val pickFromGallery = Intent(
                        Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    pickFromGallery.type = "image/*"
                    activity.startActivityForResult(pickFromGallery, 1)
                }

                choice[item] == "Take Photo" -> {
                    val cameraPicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    activity.startActivityForResult(cameraPicture, 0)
                }

                choice[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        })
        myAlertDialog.show()
    }

    @Suppress("DEPRECATION")
    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val imageSelected = data.extras!!["data"] as Bitmap?
                    imageView.setImageBitmap(imageSelected)
                }

                1 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val imageSelected = data.data
                    val pathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (imageSelected != null) {
                        val myCursor = activity.contentResolver.query(
                            imageSelected,
                            pathColumn, null, null, null
                        )
                        if (myCursor != null) {
                            myCursor.moveToFirst()
                            val columnIndex = myCursor.getColumnIndex(pathColumn[0])
                            val picturePath = myCursor.getString(columnIndex)
                            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            myCursor.close()
                        }
                    }
                }
            }
        }
    }

}