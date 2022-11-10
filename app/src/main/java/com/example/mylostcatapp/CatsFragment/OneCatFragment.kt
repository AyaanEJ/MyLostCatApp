package com.example.mylostcatapp.CatsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mylostcatapp.Models.AuthenticationViewModel
import com.example.mylostcatapp.Models.CatsViewModel
import com.example.mylostcatapp.R
import com.example.mylostcatapp.databinding.FragmentOneCatBinding


class OneCatFragment : Fragment() {
    private var _binding: FragmentOneCatBinding? = null
    private val binding get() = _binding!!
    private lateinit var mDetector: GestureDetectorCompat
    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneCatBinding.inflate(inflater, container, false)

        mDetector = GestureDetectorCompat(requireContext(), MyGestureListener())
        val rootView: LinearLayout = binding.root
        rootView.setOnTouchListener { view, motionEvent ->
            mDetector.onTouchEvent(motionEvent)
            Log.d("APPLE", "Touch: " + motionEvent.x + " " + motionEvent.y)
            true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val singleCatFragmentArgs: OneCatFragmentArgs = OneCatFragmentArgs.fromBundle(bundle)
        val position = singleCatFragmentArgs.position
        val cat = catsViewModel[position]
        if (cat == null) {
            binding.textviewSecond.text = "No such cat!"
            return
        } else {
            binding.catPresentation.text = cat.toLongString()
        }

        if(authenticationViewModel.userMutableLiveData.value?.email == cat.userId) {
            binding.buttonDeleteCat.visibility = View.VISIBLE
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.catPresentation.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.buttonDeleteCat.setOnClickListener {
            // TODO: Why does list of cats not auto-update when a cat is deleted?
            catsViewModel.delete(cat.id)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onScroll(
            ev1: MotionEvent, ev2: MotionEvent, distanceX: Float, distanceY: Float
        ): Boolean {
            Log.d("APPLE", "onScroll: $ev1 \n $ev2")
            doIt(ev1, ev2)
            return super.onScroll(ev1, ev2, distanceX, distanceY)
        }

        private fun doIt(ev1: MotionEvent, ev2: MotionEvent) {
            val xDiff = ev2.x - ev1.x
            if (xDiff > 0) {
                findNavController() // inner keyword on MyGesture Listener
                    .navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    }
}