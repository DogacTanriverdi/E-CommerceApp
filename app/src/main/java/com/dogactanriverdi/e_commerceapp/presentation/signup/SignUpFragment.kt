package com.dogactanriverdi.e_commerceapp.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            tvAlreadyHaveAnAccount.setOnClickListener {
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                findNavController().navigate(action)
            }

            btnSignUp.setOnClickListener {
                val action = SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}