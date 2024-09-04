package com.glucode.about_you.about.views

import org.junit.Assert
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.glucode.about_you.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class AnswerCardViewTest {

    private lateinit var context: Context
    private lateinit var answerCardView: AnswerCardView

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        answerCardView = AnswerCardView(context)

    }

    @Test
    fun getTitleTest() {
        val testTitle = "Test Title"
        answerCardView.title = testTitle
        assertEquals(testTitle, answerCardView.getTitleTextView().text.toString())

    }

    @Test
    fun testSelectionStateSelected() {

        answerCardView.isSelected = true
        assertEquals(
            ContextCompat.getColor(context, R.color.white),
            answerCardView.getCardBackgroundColor()
        )
        assertEquals(
            ContextCompat.getColor(context, R.color.black),
            answerCardView.getTitleTextView().currentTextColor
        )
    }

    @Test
    fun testSelectionStateDeselected() {
        answerCardView.isSelected = false
        assertEquals(
            null,
            answerCardView.cardBackgroundColor
        )
        assertEquals(
            ContextCompat.getColor(context, R.color.white),
            answerCardView.getTitleTextView().currentTextColor
        )
    }


}