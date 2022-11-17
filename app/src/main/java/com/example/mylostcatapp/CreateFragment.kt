package com.example.mylostcatapp

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.activityViewModels
import com.example.mylostcatapp.Models.AuthenticationViewModel
import com.example.mylostcatapp.Models.CatsViewModel
import com.example.mylostcatapp.databinding.FragmentCreateBinding
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.fragment.findNavController
import com.example.mylostcatapp.Models.Cat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonBack.setOnClickListener{findNavController().navigate(R.id.action_createFragment_to_ListOfCatsFragment)}

       /* binding.buttonCreateCat.setOnClickListener {
            findNavController().navigate(R.id.action_ListOfCatsFragment_to_createFragment)
        }*/

        binding.buttonCreateCat.setOnClickListener {

            val catName: String = binding.catNameInput.text.toString().trim()
            val catDescription: String = binding.catDescriptionInput.text.toString().trim()
            val catPlace: String = binding.catPlaceInput.text.toString().trim()
            val catReward: Int = binding.catRewardInput.text.toString().trim().toInt()
            val catUserID: String =
                authenticationViewModel.userMutableLiveData.value?.email.toString()


            val calendar: Calendar = Calendar.getInstance()
            calendar.set(
                binding.catDateInput.year,
                binding.catDateInput.month,
                binding.catDateInput.dayOfMonth
            )
            val catDate = calendar.timeInMillis / 1000

            val catPictureURL: String = binding.catPictureUrlInput.text.toString().trim()

            val lostCat = Cat(
                0,
                catName,
                catDescription,
                catPlace,
                catReward,
                catUserID,
                catDate,
                catPictureURL
            )

            catsViewModel.add(lostCat)

            findNavController().popBackStack()
        }
    }
    companion object {
        //@jvmStatic

        fun newInstance(param1: String, param2: String) =
            CreateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }