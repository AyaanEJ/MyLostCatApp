package com.example.mylostcatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mylostcatapp.Models.AuthenticationViewModel
import com.example.mylostcatapp.databinding.ActivityMainBinding.inflate
import com.example.mylostcatapp.databinding.ContentMainBinding.inflate
import com.example.mylostcatapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // binding.emailInputField.setText(currentUser.email)
            //current user exists: no need to login in again
            findNavController().navigate(R.id.action_loginFragment_to_FirstFragment)
        }
        binding.messageView.text = "User is ${currentUser?.email}"
        binding.signIn.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "no email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "no password"
                return@setOnClickListener
            }
            authenticationViewModel.logIn(email, password)

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_FirstFragment)
                    Snackbar.make(binding.root, "You are now signed in", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    binding.messageView.text = task.exception?.message
                }
            }
        }
        binding.buttonCreateUser.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "no email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "no password"
                return@setOnClickListener
            }

            binding.messageView.text =
                "Current user ${authenticationViewModel.userMutableLiveData.value?.toString()}"
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.messageView.text = "User created. Now please login"
                    // Alternative: goto next fragment (no need to login after register)
                } else {
                    binding.messageView.text = task.exception?.message
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


