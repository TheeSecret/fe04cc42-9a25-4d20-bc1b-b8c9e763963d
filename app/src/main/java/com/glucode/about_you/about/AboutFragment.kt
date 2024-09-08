package com.glucode.about_you.about

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.glucode.about_you.MainActivity
import com.glucode.about_you.EngineersViewModel
import com.glucode.about_you.about.views.ProfileCardView
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.engineers.models.Engineer

class AboutFragment: Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var viewModel: EngineersViewModel
    var REQUEST_CODE_PERMISSIONS =1001

    // Declare the launcher
    private val galleryLauncher= registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {

            val engineerName = arguments?.getString("name")
            // Convert the URI to a Bitmap
            val bitmap = getBitmapFromUri(uri)
            viewModel.updateEngineerImage(engineerName.toString(),bitmap)

          //  Toast.makeText(requireContext(), engineerName, Toast.LENGTH_LONG).show()

        }
    }

    // Declare the launcher
    private val cameraLauncher= registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            val engineerName = arguments?.getString("name")
            // Convert the URI to a Bitmap
           // val bitmap = getBitmapFromUri(uri)
            viewModel.updateEngineerImage(engineerName.toString(),it)

            //  Toast.makeText(requireContext(), engineerName, Toast.LENGTH_LONG).show()

        }
    }

/*
    private val cameraLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            imageView.setImageBitmap(it)
        }
    }
    */




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        val engineerName = arguments?.getString("name")

        viewModel.engineers.observe(viewLifecycleOwner, Observer {
            engineers ->
            (activity as MainActivity).selectedEngineer = (engineers.first{ it.name == engineerName }).name
            setUpProfile(engineers.first{ it.name == engineerName })
            setUpQuestions(engineers.first{ it.name == engineerName })
        })


    }

    private fun setUpQuestions(engineer: Engineer) {

        engineer.questions.forEach { question ->
            val questionView = QuestionCardView(requireContext())
            questionView.title = question.questionText
            questionView.answers = question.answerOptions
            questionView.selection = question.answer.index
            binding.container.addView(questionView)
        }
    }


    private fun setUpProfile(engineer : Engineer){
        val profileView = ProfileCardView(requireContext())

        profileView.defaultImageName = engineer.defaultImageName
        profileView.name = engineer.name
        profileView.role = engineer.role

        profileView.quickstats = engineer.quickStats

        profileView.onImageClick = { onImageClick()}

        binding.container.removeAllViews()
        binding.container.addView(profileView)
    }

    fun onImageClick() { // Launch the image picker

       galleryLauncher.launch("image/*")

    }

    // Helper function to get Bitmap from URI
    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream =  requireContext().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun checkPermissions() {
        val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        val missingPermissions = permissions.filter { ContextCompat.checkSelfPermission(requireContext() as Activity, it) != PackageManager.PERMISSION_GRANTED }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(requireContext() as Activity, missingPermissions.toTypedArray(), REQUEST_CODE_PERMISSIONS)
        }
    }


}