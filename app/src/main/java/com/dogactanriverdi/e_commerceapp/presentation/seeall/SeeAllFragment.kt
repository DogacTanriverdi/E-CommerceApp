package com.dogactanriverdi.e_commerceapp.presentation.seeall

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentSeeAllBinding
import com.dogactanriverdi.e_commerceapp.presentation.seeall.adapter.SeeAllAdapter
import com.dogactanriverdi.e_commerceapp.presentation.seeall.state.ProductsState
import com.dogactanriverdi.e_commerceapp.presentation.seeall.state.SaleProductsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeeAllFragment : Fragment(R.layout.fragment_see_all) {

    private val binding by viewBinding(FragmentSeeAllBinding::bind)

    private val viewModel: SeeAllViewModel by viewModels()

    private val seeAllAdapter by lazy { SeeAllAdapter() }

    private val args: SeeAllFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSeeAllAdapter()

        if (!args.saleState) {
            viewModel.getProducts()
            observeProductsState(viewModel.productsState)
        } else {
            viewModel.getSaleProducts()
            observeOnSaleProductsState(viewModel.saleProductsState)
        }
    }

    private fun setupSeeAllAdapter() {
        binding.rvSeeAll.adapter = seeAllAdapter

        seeAllAdapter.setOnItemClickListener { product ->
            val action = SeeAllFragmentDirections.actionSeeAllFragmentToDetailFragment(
                product.id
            )
            findNavController().navigate(action)
        }
    }

    private fun observeProductsState(productsState: StateFlow<ProductsState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            productsState.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        tvError.visibility = View.GONE
                        rvSeeAll.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visibility = View.VISIBLE
                        tvError.text = state.error
                        rvSeeAll.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }

                    state.products?.let { products ->
                        tvError.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        rvSeeAll.visibility = View.VISIBLE
                        seeAllAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }

    private fun observeOnSaleProductsState(saleState: StateFlow<SaleProductsState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            saleState.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        tvError.visibility = View.GONE
                        rvSeeAll.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visibility = View.VISIBLE
                        tvError.text = state.error
                        rvSeeAll.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }

                    state.products?.let { products ->
                        tvError.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        rvSeeAll.visibility = View.VISIBLE
                        seeAllAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }
}