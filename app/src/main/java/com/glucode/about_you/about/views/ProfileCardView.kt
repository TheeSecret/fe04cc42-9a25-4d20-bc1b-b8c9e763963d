package com.glucode.about_you.about.views

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewProfileCardBinding
import com.glucode.about_you.engineers.models.QuickStats


class ProfileCardView @JvmOverloads constructor(
    activityT: Activity,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding: ViewProfileCardBinding =
        ViewProfileCardBinding.inflate(LayoutInflater.from(context), this)

    fun profileCardBinding() = binding


    private var profileImageView: ImageView = binding.profileImage
    private lateinit var activity: Activity

    var defaultImageName: Bitmap? = null
        set(value) {
            field = value
            setupImageView(activity ,profileImageView, defaultImageName)

        }

    var name: String? = null
        set(value) {
            field = value
            binding.name.text = value
        }


    var role: String? = null
        set(value) {
            field = value
            binding.role.text = value
        }

    var quickstats: QuickStats? = null
        set(value) {
            field = value
            setUpQuickStats(context, quickstats?.years.toString(), quickstats?.coffees.toString(), quickstats?.bugs.toString())
        }




    init {
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.black))
        activity = activityT

    }

    private fun setupImageView(activity: Activity, view: ImageView, imageSelected:Bitmap?)
    {
        if(imageSelected != null) {
            //val uri = concat("@drawable/", imageSelected)

            //val imageResource = resources.getIdentifier(uri.toString(), null, context.packageName)
            //val res = resources.getDrawable(imageResource)
            view.setImageBitmap(imageSelected)


        }
        view.setOnClickListener { onImageClick(activity, view) }
    }
    private fun onImageClick(activity: Activity, view: ImageView){
        selectImage(activity )
    }

    fun selectImage(activity: Activity) {

        val choice = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        myAlertDialog.setTitle("Select Image")
        myAlertDialog.setItems(choice, DialogInterface.OnClickListener { dialog, item ->

            when {
                choice[item] == "Choose from Gallery" -> {
                    val pickFromGallery = Intent(
                        Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    ).apply {
                        type = "image/*"
                    }

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

    private fun setUpQuickStats(context: Context,years :String? , coffees :String?, bugs : String?){
        val quickStatsCardView = QuickStatsCardView(context)
        quickStatsCardView.years=years
        quickStatsCardView.coffees=coffees
        quickStatsCardView.bugs=bugs


        binding.container.addView(quickStatsCardView)
    }
}
