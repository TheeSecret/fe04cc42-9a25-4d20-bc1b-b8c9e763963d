package com.glucode.about_you.engineers.models

import android.graphics.Bitmap

data class Engineer(
    val name: String,
    val role: String,
    var defaultImageName: Bitmap?,
    val quickStats: QuickStats,
    val questions: List<Question>,
)