package com.glucode.about_you.about.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewQuickstatsCardBinding

class QuickStatsCardView  @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr)  {

    private val binding: ViewQuickstatsCardBinding =
        ViewQuickstatsCardBinding.inflate(LayoutInflater.from(context), this)

    var yearsNumber: String? = null
        set(value) {
            field = value
            binding.years.text = value
        }

    var coffeesNumber: String? = null
        set(value) {
            field = value
            binding.coffeesNumber.text = value
        }

    var bugsNumber: String? = null
        set(value) {
            field = value
            binding.bugsNumber.text = value
        }



    init {
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))

    }

}