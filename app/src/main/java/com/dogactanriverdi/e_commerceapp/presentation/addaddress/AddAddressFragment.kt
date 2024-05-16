package com.dogactanriverdi.e_commerceapp.presentation.addaddress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentAddAddressBinding
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddAddressBody
import com.dogactanriverdi.e_commerceapp.presentation.addaddress.state.AddAddressState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAddressFragment : Fragment(R.layout.fragment_add_address) {

    private val binding by viewBinding(FragmentAddAddressBinding::bind)

    private val viewModel: AddAddressViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val userId = readUserId(requireContext(), Constants.DATASTORE_USER_ID_KEY)
            userId?.let {
                with(binding) {

                    ibBack.setOnClickListener {
                        findNavController().navigateUp()
                    }

                    btnSave.setOnClickListener {
                        viewModel.addAddress(
                            AddAddressBody(
                                userId = userId,
                                title = etAddressTitle.text.toString(),
                                address = etAddressDescription.text.toString()
                            )
                        )
                    }
                }
            }
        }

        observeAddAddress(viewModel.addAddressState)
    }

    private fun observeAddAddress(state: StateFlow<AddAddressState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        progressBar.visibility = View.VISIBLE
                        tvError.visibility = View.GONE
                        addAddressLayout.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        progressBar.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                        addAddressLayout.visibility = View.GONE
                        tvError.text = state.error
                    }

                    state.addAddress?.let { response ->
                        progressBar.visibility = View.GONE
                        tvError.visibility = View.GONE
                        addAddressLayout.visibility = View.VISIBLE
                        Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT).show()
                        if (response.status == 200) {
                            etAddressTitle.text?.clear()
                            etAddressDescription.text?.clear()
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }
}