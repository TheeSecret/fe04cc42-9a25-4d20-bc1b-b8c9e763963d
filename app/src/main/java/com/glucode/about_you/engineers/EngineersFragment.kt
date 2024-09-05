package com.glucode.about_you.engineers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.glucode.about_you.EngineersViewModel
import com.glucode.about_you.MainActivity
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData.engineers

class EngineersFragment : Fragment() {
    private lateinit var binding: FragmentEngineersBinding

    lateinit var viewModel: EngineersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        viewModel.engineers.observe(viewLifecycleOwner) { engineers ->
            setUpEngineersList(engineers)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


       val sortedEngineers = when(item.itemId ) {

           R.id.action_years -> engineers.sortedBy { it.quickStats.years }
           R.id.action_bugs -> engineers.sortedBy { it.quickStats.bugs }
           R.id.action_coffees -> engineers.sortedBy { it.quickStats.coffees }
           else -> return super.onOptionsItemSelected(item)
       }
        // Update the RecyclerView with the sorted list
        setUpEngineersList(sortedEngineers)

            return true

    }

    private fun setUpEngineersList(engineers: List<Engineer>) {



        binding.list.adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(dividerItemDecoration)
    }

    private fun goToAbout(engineer: Engineer) {
        val bundle = Bundle().apply {
            putString("name", engineer.name)
        }
        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)

    }





}