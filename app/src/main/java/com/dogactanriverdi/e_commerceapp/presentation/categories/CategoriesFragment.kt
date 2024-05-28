package com.dogactanriverdi.e_commerceapp.presentation.categories

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
import com.dogactanriverdi.e_commerceapp.databinding.FragmentCategoriesBinding
import com.dogactanriverdi.e_commerceapp.presentation.categories.adapter.CategoriesAdapter
import com.dogactanriverdi.e_commerceapp.presentation.categories.state.ProductsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val binding by viewBinding(FragmentCategoriesBinding::bind)

    private val viewModel: CategoriesViewModel by viewModels()

    private val categoriesAdapter by lazy { CategoriesAdapter() }

    private val args: CategoriesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productsState = viewModel.productsState

        viewModel.getProductsByCategory(args.categoryName)

        setupCategoriesAdapter()

        observeProductsState(productsState)
    }

    private fun setupCategoriesAdapter() {
        binding.rvCategories.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener { product ->
            val action =
                CategoriesFragmentDirections.actionCategoriesFragmentToDetailFragment(
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
                        rvCategories.gone()
                        progressBar.gone()
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visible()
                        tvError.text = state.error
                        rvCategories.gone()
                        progressBar.gone()
                    }

                    state.products?.let { response ->
                        tvError.gone()
                        progressBar.gone()
                        rvCategories.visible()
                        categoriesAdapter.recyclerListDiffer.submitList(response.products)
                    }
                }
            }
        }
    }
}