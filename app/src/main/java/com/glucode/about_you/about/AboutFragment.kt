package com.glucode.about_you.about

import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.glucode.about_you.about.views.ProfileCardView
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.about.views.QuickStatsCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData

class AboutFragment: Fragment() {
    private lateinit var binding: FragmentAboutBinding

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

        val engineerName = arguments?.getString("name")

        val engineer = MockData.engineers.first { it.name == engineerName }

        setUpProfile(engineer)
        setUpQuestions(engineer)
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

        if ( engineer.defaultImageName.isNotEmpty()) {
     //   profileView.image = engineer.defaultImageName
         }

          profileView.name = engineer.name
          profileView.role = engineer.role

        binding.container.addView(profileView)
    }


     private fun setUpQuickStats(engineer : Engineer){
         val quickStatsView = QuickStatsCardView(requireContext())


            quickStatsView.bugsNumber = engineer.quickStats.bugs.toString()
            quickStatsView.coffeesNumber = engineer.quickStats.coffees.toString()
            quickStatsView.yearsNumber = engineer.quickStats.toString()


         binding.container.addView(quickStatsView )
     }







}