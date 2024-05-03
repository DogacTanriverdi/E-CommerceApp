package com.dogactanriverdi.e_commerceapp.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentProfileBinding
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.EditProfileBody
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.ChangePasswordState
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.EditProfileState
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.UserState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userState = viewModel.userState
        val editProfileState = viewModel.editProfileState
        val changePasswordState = viewModel.changePasswordState

        lifecycleScope.launch {
            val userId = readUserId(requireContext(), DATASTORE_USER_ID_KEY)
            userId?.let {
                with(binding) {

                    viewModel.getUser(userId)

                    btnSave.setOnClickListener {
                        viewModel.editProfile(
                            EditProfileBody(
                                "",
                                etName.text.toString(),
                                etPhone.text.toString(),
                                userId
                            )
                        )
                    }

                    btnChangePassword.setOnClickListener {
                        val newPassword = etNewPassword.text.toString()
                        val verifyPassword = etVerifyPassword.text.toString()

                        if (newPassword == verifyPassword) {
                            viewModel.changePassword(
                                ChangePasswordBody(newPassword, userId)
                            )
                        } else {
                            tilVerifyPassword.error = getString(R.string.passwords_do_not_match)
                        }
                    }
                }
            }
        }

        observeGetUser(userState)
        observeEditProfile(editProfileState)
        observeChangePassword(changePasswordState)
    }

    private fun observeChangePassword(state: StateFlow<ChangePasswordState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        btnChangePassword.isEnabled = false
                        pbChangePassword.visibility = View.VISIBLE
                        tvChangePasswordError.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        btnChangePassword.isEnabled = true
                        pbChangePassword.visibility = View.GONE
                        tvChangePasswordError.visibility = View.VISIBLE
                        tvChangePasswordError.text = state.error
                    }

                    state.changePassword?.let { user ->
                        btnChangePassword.isEnabled = true
                        pbChangePassword.visibility = View.GONE
                        tvChangePasswordError.visibility = View.GONE
                        Snackbar.make(requireView(), user.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeEditProfile(state: StateFlow<EditProfileState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        btnSave.isEnabled = false
                        pbEditProfile.visibility = View.VISIBLE
                        tvEditProfileError.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        btnSave.isEnabled = true
                        pbEditProfile.visibility = View.GONE
                        tvEditProfileError.visibility = View.VISIBLE
                        tvEditProfileError.text = state.error
                    }

                    state.editProfile?.let { user ->
                        btnSave.isEnabled = true
                        pbEditProfile.visibility = View.GONE
                        tvEditProfileError.visibility = View.GONE
                        Snackbar.make(requireView(), user.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeGetUser(userState: StateFlow<UserState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            userState.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        progressBar.visibility = View.VISIBLE
                        nestedScrollView.visibility = View.GONE
                        tvError.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        progressBar.visibility = View.GONE
                        nestedScrollView.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                        tvError.text = state.error
                    }

                    state.user?.user?.let { user ->
                        progressBar.visibility = View.GONE
                        nestedScrollView.visibility = View.VISIBLE
                        tvError.visibility = View.GONE

                        etName.setText(user.name)
                        etPhone.setText(user.phone)
                    }
                }
            }
        }
    }
}