package com.dogactanriverdi.e_commerceapp.presentation.seeall

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
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

        val productsState = viewModel.productsState
        val saleProductsState = viewModel.saleProductsState

        setupSeeAllAdapter()

        if (!args.saleState) {
            viewModel.getProducts()
            observeProductsState(productsState)
        } else {
            viewModel.getSaleProducts()
            observeOnSaleProductsState(saleProductsState)
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

    private fun observeProductsState(state: StateFlow<ProductsState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        tvError.gone()
                        rvSeeAll.gone()
                        progressBar.visible()
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visible()
                        tvError.text = state.error
                        rvSeeAll.gone()
                        progressBar.gone()
                    }

                    state.products?.let { products ->
                        tvError.gone()
                        progressBar.gone()
                        rvSeeAll.visible()
                        seeAllAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }

    private fun observeOnSaleProductsState(state: StateFlow<SaleProductsState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        tvError.gone()
                        rvSeeAll.gone()
                        progressBar.visible()
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visible()
                        tvError.text = state.error
                        rvSeeAll.gone()
                        progressBar.gone()
                    }

                    state.products?.let { products ->
                        tvError.gone()
                        progressBar.gone()
                        rvSeeAll.visible()
                        seeAllAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }
}