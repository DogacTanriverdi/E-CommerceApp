package com.dogactanriverdi.e_commerceapp.presentation.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.saveUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
import com.dogactanriverdi.e_commerceapp.databinding.FragmentSignInBinding
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignInBody
import com.dogactanriverdi.e_commerceapp.presentation.signin.state.SignInState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val userId = readUserId(requireContext(), DATASTORE_USER_ID_KEY)
            userId?.let {
                userId.isNotBlank().let {
                    val action =
                        SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInState = viewModel.signInState

        with(binding) {

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModel.signIn(
                    SignInBody(email, password)
                )
            }

            tvDontHaveAnAccount.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                findNavController().navigate(action)
            }
        }

        observeSignInState(signInState)
    }

    private fun observeSignInState(state: StateFlow<SignInState>) {
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

                    state.signIn?.let { signIn ->
                        progressBar.gone()

                        if (signIn.status == 200) {

                            saveUserId(requireContext(), DATASTORE_USER_ID_KEY, signIn.userId)

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
    }
}