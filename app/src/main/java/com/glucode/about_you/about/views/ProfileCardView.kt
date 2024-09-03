package com.glucode.about_you.about.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewProfileCardBinding

class ProfileCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding: ViewProfileCardBinding =
        ViewProfileCardBinding.inflate(LayoutInflater.from(context), this)


    var image: Drawable? = null
        set(value) {
            field = value
            binding.profileImage.setImageDrawable(value)
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

    init {
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.black))
    }




}
