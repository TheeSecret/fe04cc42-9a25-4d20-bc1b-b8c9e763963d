package com.glucode.about_you.about.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewQuickStatsCardBinding

class QuickStatsCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding: ViewQuickStatsCardBinding =
        ViewQuickStatsCardBinding.inflate(LayoutInflater.from(context), this)

    init{
        radius = resources.getDimension(R.dimen.corner_radius_large)
        elevation = resources.getDimension(R.dimen.elevation_normal)
    }

    var years: String? = null
        set(value) {
            field = value
            binding.years.text = value
        }

    var coffees: String? = null
        set(value) {
            field = value
            binding.coffees.text = value
        }

    var bugs: String? = null
        set(value) {
            field = value
            binding.bugs.text = value
        }


}