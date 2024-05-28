package com.dogactanriverdi.e_commerceapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
import com.dogactanriverdi.e_commerceapp.databinding.FragmentHomeBinding
import com.dogactanriverdi.e_commerceapp.presentation.home.adapter.CategoriesAdapter
import com.dogactanriverdi.e_commerceapp.presentation.home.adapter.OnSaleAdapter
import com.dogactanriverdi.e_commerceapp.presentation.home.adapter.ProductAdapter
import com.dogactanriverdi.e_commerceapp.presentation.home.state.CategoriesState
import com.dogactanriverdi.e_commerceapp.presentation.home.state.ProductsState
import com.dogactanriverdi.e_commerceapp.presentation.home.state.SaleProductsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

    private val productAdapter by lazy { ProductAdapter() }
    private val onSaleAdapter by lazy { OnSaleAdapter() }
    private val categoriesAdapter by lazy { CategoriesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

        val productsState = viewModel.productsState
        val saleProductsState = viewModel.saleProductsState
        val categoriesState = viewModel.categoriesState

        with(binding) {

            tvSeeAllProducts.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSeeAllFragment(saleState = false)
                findNavController().navigate(action)
            }

            tvSeeAllOnSale.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSeeAllFragment(saleState = true)
                findNavController().navigate(action)
            }

            ibCart.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCartFragment()
                findNavController().navigate(action)
            }
        }

        setupProductAdapter()
        setupOnSaleAdapter()
        setupCategoriesAdapter()

        observeProductsState(productsState)
        observeOnSaleProductsState(saleProductsState)
        observeCategoriesState(categoriesState)
    }

    private fun setupProductAdapter() {
        binding.rvProducts.adapter = productAdapter

        productAdapter.setOnItemClickListener { product ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(product.id)
            findNavController().navigate(action)
        }
    }

    private fun setupOnSaleAdapter() {
        binding.rvOnSaleProducts.adapter = onSaleAdapter

        onSaleAdapter.setOnItemClickListener { product ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(product.id)
            findNavController().navigate(action)
        }
    }

    private fun setupCategoriesAdapter() {
        binding.rvCategories.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener { category ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToCategoriesFragment(
                    category.name
                )
            findNavController().navigate(action)
        }
    }

    private fun observeProductsState(state: StateFlow<ProductsState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        tvErrorProducts.gone()
                        rvProducts.gone()
                        pbProducts.visible()
                    }

                    if (state.error.isNotBlank()) {
                        tvErrorProducts.visible()
                        tvErrorProducts.text = state.error
                        rvProducts.gone()
                        pbProducts.gone()
                    }

                    state.products?.let { response ->
                        tvErrorProducts.gone()
                        pbProducts.gone()
                        rvProducts.visible()
                        productAdapter.recyclerListDiffer.submitList(response.products)
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
                        tvErrorOnSale.gone()
                        rvOnSaleProducts.gone()
                        pbOnSale.visible()
                    }

                    if (state.error.isNotBlank()) {
                        tvErrorOnSale.visible()
                        tvErrorOnSale.text = state.error
                        rvOnSaleProducts.gone()
                        pbOnSale.gone()
                    }

                    state.products?.let { response ->
                        tvErrorOnSale.gone()
                        pbOnSale.gone()
                        rvOnSaleProducts.visible()
                        onSaleAdapter.recyclerListDiffer.submitList(response.products)
                    }
                }
            }
        }
    }

    private fun observeCategoriesState(state: StateFlow<CategoriesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        tvErrorCategories.gone()
                        rvCategories.gone()
                        pbCategories.visible()
                    }

                    if (state.error.isNotBlank()) {
                        tvErrorCategories.visible()
                        tvErrorCategories.text = state.error
                        rvCategories.gone()
                        pbCategories.gone()
                    }

                    state.categories?.let { response ->
                        tvErrorCategories.gone()
                        pbCategories.gone()
                        rvCategories.visible()
                        categoriesAdapter.recyclerListDiffer.submitList(response.categories)
                    }
                }
            }
        }
    }
}