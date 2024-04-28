package com.dogactanriverdi.e_commerceapp.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentSignUpBinding
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody
import com.dogactanriverdi.e_commerceapp.presentation.signin.SignInFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val state = viewModel.state

        with(binding) {

            btnSignUp.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModel.signUp(
                    SignUpBody(
                        name = name,
                        email = email,
                        password = password,
                        address = "",
                        phone = ""
                    )
                )

                viewLifecycleOwner.lifecycleScope.launch {
                    state.collect { state ->

                        if (state.isLoading) {
                            progressBar.visibility = View.VISIBLE
                            tvError.visibility = View.GONE
                        }

                        if (state.error.isNotBlank()) {
                            progressBar.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            tvError.text = state.error
                        }

                        state.signUp?.let { signUp ->
                            progressBar.visibility = View.GONE

                            if (signUp.status == 200) {
                                val action =
                                    SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()

                                findNavController().navigate(action)

                                Toast.makeText(
                                    requireContext(),
                                    R.string.successfully_signed_up,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            tvAlreadyHaveAnAccount.setOnClickListener {
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                findNavController().navigate(action)
            }
        }
    }
}