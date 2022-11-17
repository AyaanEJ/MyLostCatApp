package com.example.mylostcatapp.CatsFragment

import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_TEXT
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mylostcatapp.Models.AuthenticationViewModel
import com.example.mylostcatapp.Models.CatsViewModel
import com.example.mylostcatapp.MyAdapter
import com.example.mylostcatapp.R
import com.example.mylostcatapp.databinding.FragmentCatListBinding

class ListOfCatsFragments : Fragment() {

    private var _binding: FragmentCatListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    // AuthenticationViewModel checks if there is a user currently logged in
    // if yes, button to create a cat is shown
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        // when create cat button is clicked, move to create cat fragment
        binding.buttonCreateCat.setOnClickListener {
            findNavController().navigate(R.id.action_ListOfCatsFragment_to_createFragment)
        }
       /* binding.buttonLogin.setOnClickListener{findNavController().navigate(R.id.action_FirstFragment_to_loginFragment)}*/

        authenticationViewModel.userMutableLiveData.observe(viewLifecycleOwner) { firebaseUser ->
          if (firebaseUser != null) {
              binding.buttonCreateCat.visibility = View.VISIBLE
          }
        }

        // CatsViewModel checks if there are any cats in the cat list
        // if not, the recyclerview which holds the cats is not shown
        catsViewModel.catsLiveData.observe(viewLifecycleOwner) { cats ->
            binding.recyclerView.visibility = if (cats == null) View.GONE else View.VISIBLE

            Log.d("APPLE", "before if")
            if (cats != null) {
                val adapter = MyAdapter(cats) { position ->
                    Log.d("Apple", "number $position was pressed")
                    val action =
                        ListOfCatsFragmentsDirections.actionFirstFragmentToSecondFragment(position)
                    findNavController().navigate(action)
                }
                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter
            }
        }
        binding.buttonSort.setOnClickListener {
            when(binding.spinnerSorting.selectedItemPosition) {
                0 -> catsViewModel.sortByReward()
                1 -> catsViewModel.sortByRewardDescending()
                2 -> catsViewModel.sortByDate()
                3 -> catsViewModel.sortByDateDescending()
            }
        }

        binding.filterParameter.setOnClickListener {
            binding.filterParameter.text.clear()
            when (binding.spinnerFiltering.selectedItemPosition) {
                0 -> binding.filterParameter.inputType = TYPE_CLASS_NUMBER
                1 -> binding.filterParameter.inputType = TYPE_CLASS_TEXT
            }
        }
        binding.buttonFilter.setOnClickListener {
            val filterParameter = binding.filterParameter.text.toString().trim()
            if (filterParameter.isEmpty()) {
                binding.filterParameter.error = "Write something!"
                return@setOnClickListener
            }
            when (binding.spinnerFiltering.selectedItemPosition) {
                0 -> catsViewModel.filterByReward(filterParameter.toInt())
                1 -> catsViewModel.filterByPlace(filterParameter)
            }
        }


        }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}
