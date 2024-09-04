package com.glucode.about_you.about.views

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.glucode.about_you.engineers.models.QuickStats
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class ProfileCardViewTest {

    private lateinit var context: Context
    private lateinit var activity: Activity
    private lateinit var profileCardView: ProfileCardView

    @Mock
    private lateinit var mockBitmap: Bitmap

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        context = ApplicationProvider.getApplicationContext()
        activity = Mockito.mock(Activity::class.java)
        profileCardView = ProfileCardView(activity, context)
    }

    @Test
    fun testDefaultImageName() {
        profileCardView.defaultImageName = mockBitmap
        assertEquals(mockBitmap, (profileCardView.profileCardBinding().profileImage.drawable as BitmapDrawable).bitmap)
    }

    @Test
    fun testNameSetter() {
        val name = "John Doe"
        profileCardView.name = name
        assertEquals(name, profileCardView.profileCardBinding().name.text)
    }

    @Test
    fun testRoleSetter() {
        val role = "Developer"
        profileCardView.role = role
        assertEquals(role, profileCardView.profileCardBinding().role.text)
    }

    @Test
    fun testQuickStatsSetter() {
        val quickStats = QuickStats(years = 5, coffees = 100, bugs = 3)
        profileCardView.quickstats = quickStats

        val quickStatsCardView = profileCardView.profileCardBinding().container.getChildAt(0) as QuickStatsCardView

        assertEquals("5", quickStatsCardView.years)
        assertEquals("100", quickStatsCardView.coffees)
        assertEquals("3", quickStatsCardView.bugs)
    }

    @Test
    fun testImageViewClickListener() {
        val imageView = profileCardView.profileCardBinding().profileImage

        imageView.performClick()

        Mockito.verify(activity).startActivityForResult(Mockito.any(Intent::class.java), Mockito.anyInt())
    }

    @Test
    fun testSelectImageFromGallery() {
        // Here we need to simulate the AlertDialog and its button clicks

        // Use Mockito to trigger the 'Choose from Gallery' scenario
        val dialogInterface = Mockito.mock(DialogInterface::class.java)
        val itemIndex = 1  // Index for 'Choose from Gallery' in the array
        profileCardView.selectImage(activity)

        // Simulate user clicking 'Choose from Gallery'
        Mockito.verify(activity).startActivityForResult(
            Mockito.argThat<Intent> {
                it.action == Intent.ACTION_GET_CONTENT && it.type == "image/*"
            },
            Mockito.eq(1)
        )
    }

    @Test
    fun testSelectImageFromCamera() {
        // Here we need to simulate the AlertDialog and its button clicks

        // Use Mockito to trigger the 'Take Photo' scenario
        val dialogInterface = Mockito.mock(DialogInterface::class.java)
        val itemIndex = 0  // Index for 'Take Photo' in the array
        profileCardView.selectImage(activity)

        // Simulate user clicking 'Take Photo'
        Mockito.verify(activity).startActivityForResult(
            Mockito.argThat<Intent> {
                it.action == MediaStore.ACTION_IMAGE_CAPTURE
            },
            Mockito.eq(0)
        )
    }
}
