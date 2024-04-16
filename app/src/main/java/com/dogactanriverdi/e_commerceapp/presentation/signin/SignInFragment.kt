package com.dogactanriverdi.e_commerceapp.presentation.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentSignInBinding

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            tvDontHaveAnAccount.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                findNavController().navigate(action)
            }

            btnSignIn.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}