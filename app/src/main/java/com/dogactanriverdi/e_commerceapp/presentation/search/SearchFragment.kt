package com.dogactanriverdi.e_commerceapp.presentation.search

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
import com.dogactanriverdi.e_commerceapp.databinding.FragmentSearchBinding
import com.dogactanriverdi.e_commerceapp.presentation.search.adapter.SearchAdapter
import com.dogactanriverdi.e_commerceapp.presentation.search.state.SearchState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel: SearchViewModel by viewModels()

    private val searchAdapter by lazy { SearchAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

        val searchState = viewModel.searchState

        with(binding) {
            val query = etSearch
            val tilSearch = tilSearch

            query.addTextChangedListener { editTextNullable ->
                editTextNullable?.let { editText ->
                    if (editText.isNotBlank()) {
                        rvSearch.visible()
                        val searchQuery = editText.toString()
                        viewModel.searchProduct(searchQuery)
                    } else {
                        rvSearch.gone()
                        return@let
                    }
                }
            }

            tilSearch.setEndIconOnClickListener {
                tilSearch.editText?.text?.clear()
            }
        }

        setupSearchAdapter()
        observeSearchProduct(searchState)
    }

    private fun setupSearchAdapter() {
        binding.rvSearch.adapter = searchAdapter

        searchAdapter.setOnItemClickListener { product ->
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                product.id
            )
            findNavController().navigate(action)
        }
    }

    private fun observeSearchProduct(state: StateFlow<SearchState>) {
        viewLifecycleOwner.lifecycleScope.launch {

            with(binding) {

                state.collect { state ->

                    if (state.isLoading) {
                        tvError.gone()
                        rvSearch.gone()
                        progressBar.visible()
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visible()
                        tvError.text = state.error
                        rvSearch.gone()
                        progressBar.gone()
                    }

                    state.search?.let { products ->
                        tvError.gone()
                        progressBar.gone()
                        rvSearch.visible()
                        searchAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }
}