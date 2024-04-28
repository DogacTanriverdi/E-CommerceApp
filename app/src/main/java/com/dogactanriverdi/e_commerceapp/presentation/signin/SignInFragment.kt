package com.dogactanriverdi.e_commerceapp.presentation.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentSignInBinding
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignInBody
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val state = viewModel.state

        with(binding) {

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModel.signIn(
                    SignInBody(email, password)
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

                        state.signIn?.let { signIn ->
                            progressBar.visibility = View.GONE

                            if (signIn.status == 200) {
                                val action =
                                    SignInFragmentDirections.actionSignInFragmentToHomeFragment()

                                findNavController().navigate(action)

                                Toast.makeText(
                                    requireContext(),
                                    R.string.successfully_signed_in,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            tvDontHaveAnAccount.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                findNavController().navigate(action)
            }
        }
    }
}