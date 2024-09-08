package com.glucode.about_you.about

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
    var selectedEngineer : String = ""
    val MY_REQUEST_CODE =123

    val activityResultsLucher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == Activity.RESULT_OK && result?.data != null) {

            when (MY_REQUEST_CODE) {
                0 -> {
                    val imageSelected = result?.data!!.extras!!["data"] as Bitmap?
                    val imageBitMap = imageSelected

                    viewModel.updateEngineerImage(selectedEngineer, imageBitMap)

                }

                1 -> {
                    val imageSelected = result.data!!.data
                    val pathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (imageSelected != null) {
                        val myCursor = requireContext().contentResolver.query(
                            imageSelected,
                            pathColumn, null, null, null
                        )
                        if (myCursor != null) {
                            myCursor.moveToFirst()
                            val columnIndex = myCursor.getColumnIndex(pathColumn[0])
                            val picturePath = myCursor.getString(columnIndex)
                            myCursor.close()
                            val imageBitMap =  BitmapFactory.decodeFile(picturePath)

                            viewModel.updateEngineerImage(selectedEngineer, imageBitMap)

                        }
                    }
                }
            }
        }

    }

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
        val profileView = ProfileCardView((activity as MainActivity), requireContext())

        profileView.defaultImageName = engineer.defaultImageName
        profileView.name = engineer.name
        profileView.role = engineer.role

        profileView.quickstats = engineer.quickStats

        binding.container.removeAllViews()
        binding.container.addView(profileView)
    }



}