package com.dogactanriverdi.e_commerceapp.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.saveUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
import com.dogactanriverdi.e_commerceapp.databinding.FragmentSignUpBinding
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody
import com.dogactanriverdi.e_commerceapp.presentation.signup.state.SignUpState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpState = viewModel.signUpState

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
            }

            tvAlreadyHaveAnAccount.setOnClickListener {
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                findNavController().navigate(action)
            }
        }

        observeSignUpState(signUpState)
    }

    private fun observeSignUpState(state: StateFlow<SignUpState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        progressBar.visible()
                        tvError.gone()
                    }

                    if (state.error.isNotBlank()) {
                        progressBar.gone()
                        tvError.visible()
                        tvError.text = state.error
                    }

                    state.signUp?.let { signUp ->
                        progressBar.gone()

                        if (signUp.status == 200) {

                            saveUserId(requireContext(), DATASTORE_USER_ID_KEY, signUp.userId)

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
    }
}