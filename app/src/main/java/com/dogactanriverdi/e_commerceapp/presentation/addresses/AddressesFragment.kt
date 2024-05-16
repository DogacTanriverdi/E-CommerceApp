package com.dogactanriverdi.e_commerceapp.presentation.addresses

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentAddressesBinding
import com.dogactanriverdi.e_commerceapp.presentation.addresses.adapter.AddressesAdapter
import com.dogactanriverdi.e_commerceapp.presentation.addresses.state.AddressesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressesFragment : Fragment(R.layout.fragment_addresses) {

    private val binding by viewBinding(FragmentAddressesBinding::bind)

    private val viewModel: AddressesViewModel by viewModels()

    private val addressesAdapter by lazy { AddressesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressesState = viewModel.addressesState

        lifecycleScope.launch {
            val userId = readUserId(requireContext(), DATASTORE_USER_ID_KEY)
            userId?.let {
                with(binding) {

                    viewModel.getAddresses(userId)

                    ibBack.setOnClickListener {
                        findNavController().navigateUp()
                    }

                    btnAddAddress.setOnClickListener {
                        val action =
                            AddressesFragmentDirections.actionAddressesFragmentToAddAddressFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }

        setupAddressesAdapter()

        observeAddresses(addressesState)
    }

    private fun setupAddressesAdapter() {
        binding.rvAddresses.adapter = addressesAdapter
    }

    private fun observeAddresses(state: StateFlow<AddressesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        progressBar.visibility = View.VISIBLE
                        rvAddresses.visibility = View.GONE
                        tvError.visibility = View.GONE
                        btnAddAddress.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        progressBar.visibility = View.GONE
                        rvAddresses.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                        btnAddAddress.visibility = View.GONE
                        tvError.text = state.error
                    }

                    state.addresses?.let { addresses ->
                        progressBar.visibility = View.GONE
                        tvError.visibility = View.GONE
                        btnAddAddress.visibility = View.VISIBLE
                        rvAddresses.visibility = View.VISIBLE
                        addressesAdapter.recyclerListDiffer.submitList(addresses.addresses)
                    }
                }
            }
        }
    }
}