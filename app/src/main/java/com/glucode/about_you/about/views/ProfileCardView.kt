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
import com.glucode.about_you.about.AboutFragment
import com.glucode.about_you.databinding.ViewProfileCardBinding
import com.glucode.about_you.engineers.models.QuickStats


class ProfileCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding: ViewProfileCardBinding =
        ViewProfileCardBinding.inflate(LayoutInflater.from(context), this)

    fun profileCardBinding() = binding


    private var profileImageView: ImageView = binding.profileImage

    var defaultImageName: Bitmap? = null
        set(value) {
            field = value
          if (defaultImageName !=null )  profileImageView.setImageBitmap(value)

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


    var onImageClick : () -> Unit = {}

    init {
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.black))
        binding.profileImage.setOnClickListener { onImageClick() }

    }

    private fun setUpQuickStats(context: Context,years :String? , coffees :String?, bugs : String?){
        val quickStatsCardView = QuickStatsCardView(context)
        quickStatsCardView.years=years
        quickStatsCardView.coffees=coffees
        quickStatsCardView.bugs=bugs

        binding.container.addView(quickStatsCardView)
    }
}
