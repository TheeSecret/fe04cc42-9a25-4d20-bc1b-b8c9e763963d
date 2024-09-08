package com.glucode.about_you.engineers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import com.glucode.about_you.about.AboutFragment

class ImagePickerContract : ActivityResultContract<Unit, Uri?>(){
    override fun createIntent(context: Context, input: Unit?): Intent {

     return   Intent(
            Intent.ACTION_GET_CONTENT,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).apply {
            type = "image/*"
        }


    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {

        return intent?.data
    }


}

