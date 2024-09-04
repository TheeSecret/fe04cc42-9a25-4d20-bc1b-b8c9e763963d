package com.glucode.about_you.about.views

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.glucode.about_you.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.*

import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
@SmallTest
class QuestionCardViewTest {

    private lateinit var questionCardView: QuestionCardView
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        questionCardView = QuestionCardView(context)
    }

    @Test
    fun testSetTitle() {
        val title = "Sample Title"
        questionCardView.title = title
        assertEquals(title, questionCardView.questionCardViewBinding().title.text)

        assertEquals(title, questionCardView.questionCardViewBinding().title.text)

    }

    @Test
    fun testAddAnswers() {
        val answers = listOf("Answer 1", "Answer 2", "Answer 3")
        questionCardView.answers = answers

        assertEquals(answers.size, questionCardView.questionCardViewBinding().answers.childCount)
        answers.forEachIndexed { index, answer ->
            val answerView = questionCardView.questionCardViewBinding().answers.getChildAt(index) as AnswerCardView
            assertEquals(answer, answerView.title)
        }
    }

    @Test
    fun testSelectAnswer() {
        val answers = listOf("Answer 1", "Answer 2", "Answer 3")
        questionCardView.answers = answers

        val firstAnswerView = questionCardView.questionCardViewBinding().answers.getChildAt(0)
        questionCardView.selection = 0
        assertTrue(firstAnswerView.isSelected)
    }



}