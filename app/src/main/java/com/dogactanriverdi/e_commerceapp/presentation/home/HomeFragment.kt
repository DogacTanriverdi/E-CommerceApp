package com.dogactanriverdi.e_commerceapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.viewBinding
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

        val productsState = viewModel.productsState
        val saleProductsState = viewModel.saleProductsState
        val categoriesState = viewModel.categoriesState

        with(binding) {

            tvSeeAllProducts.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSeeAllFragment(0)
                findNavController().navigate(action)
            }

            tvSeeAllOnSale.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSeeAllFragment(1)
                findNavController().navigate(action)
            }
        }

        observeProducts(productsState)
        observeOnSaleProducts(saleProductsState)
        observeCategories(categoriesState)

        setupProductAdapter()
        setupOnSaleAdapter()
        setupCategoriesAdapter()
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

    private fun observeProducts(productsState: StateFlow<ProductsState>) {
        viewLifecycleOwner.lifecycleScope.launch {

            with(binding) {

                productsState.collect { state ->

                    if (state.isLoading) {
                        tvErrorProducts.visibility = View.GONE
                        rvProducts.visibility = View.GONE
                        pbProducts.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        tvErrorProducts.visibility = View.VISIBLE
                        tvErrorProducts.text = state.error
                        rvProducts.visibility = View.GONE
                        pbProducts.visibility = View.GONE
                    }

                    state.products?.let { products ->
                        tvErrorProducts.visibility = View.GONE
                        pbProducts.visibility = View.GONE
                        rvProducts.visibility = View.VISIBLE
                        productAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }

    private fun observeOnSaleProducts(saleState: StateFlow<SaleProductsState>) {
        viewLifecycleOwner.lifecycleScope.launch {

            with(binding) {

                saleState.collect { state ->

                    if (state.isLoading) {
                        tvErrorOnSale.visibility = View.GONE
                        rvOnSaleProducts.visibility = View.GONE
                        pbOnSale.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        tvErrorOnSale.visibility = View.VISIBLE
                        tvErrorOnSale.text = state.error
                        rvOnSaleProducts.visibility = View.GONE
                        pbOnSale.visibility = View.GONE
                    }

                    state.products?.let { products ->
                        tvErrorOnSale.visibility = View.GONE
                        pbOnSale.visibility = View.GONE
                        rvOnSaleProducts.visibility = View.VISIBLE
                        onSaleAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }

    private fun observeCategories(categoriesState: StateFlow<CategoriesState>) {
        viewLifecycleOwner.lifecycleScope.launch {

            with(binding) {

                categoriesState.collect { state ->

                    if (state.isLoading) {
                        tvErrorCategories.visibility = View.GONE
                        rvCategories.visibility = View.GONE
                        pbCategories.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        tvErrorCategories.visibility = View.VISIBLE
                        tvErrorCategories.text = state.error
                        rvCategories.visibility = View.GONE
                        pbCategories.visibility = View.GONE
                    }

                    state.categories?.let { categories ->
                        tvErrorCategories.visibility = View.GONE
                        pbCategories.visibility = View.GONE
                        rvCategories.visibility = View.VISIBLE
                        categoriesAdapter.recyclerListDiffer.submitList(categories.categories)
                    }
                }
            }
        }
    }
}