package com.dogactanriverdi.e_commerceapp.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
import com.dogactanriverdi.e_commerceapp.databinding.FragmentProfileBinding
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.EditProfileBody
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.ChangePasswordState
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.EditProfileState
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.UserState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

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

                    btnGoToAddresses.setOnClickListener {
                        val action =
                            ProfileFragmentDirections.actionProfileFragmentToAddressesFragment()
                        findNavController().navigate(action)
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
                        pbChangePassword.visible()
                        tvChangePasswordError.gone()
                    }

                    if (state.error.isNotBlank()) {
                        btnChangePassword.isEnabled = true
                        pbChangePassword.gone()
                        tvChangePasswordError.visible()
                        tvChangePasswordError.text = state.error
                    }

                    state.changePassword?.let { user ->
                        btnChangePassword.isEnabled = true
                        pbChangePassword.gone()
                        tvChangePasswordError.gone()
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
                        pbEditProfile.visible()
                        tvEditProfileError.gone()
                    }

                    if (state.error.isNotBlank()) {
                        btnSave.isEnabled = true
                        pbEditProfile.gone()
                        tvEditProfileError.visible()
                        tvEditProfileError.text = state.error
                    }

                    state.editProfile?.let { user ->
                        btnSave.isEnabled = true
                        pbEditProfile.gone()
                        tvEditProfileError.gone()
                        Snackbar.make(requireView(), user.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeGetUser(state: StateFlow<UserState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        progressBar.visible()
                        nestedScrollView.gone()
                        tvError.gone()
                    }

                    if (state.error.isNotBlank()) {
                        progressBar.gone()
                        nestedScrollView.gone()
                        tvError.visible()
                        tvError.text = state.error
                    }

                    state.user?.user?.let { user ->
                        progressBar.gone()
                        nestedScrollView.visible()
                        tvError.gone()

                        etName.setText(user.name)
                        etPhone.setText(user.phone)
                    }
                }
            }
        }
    }
}