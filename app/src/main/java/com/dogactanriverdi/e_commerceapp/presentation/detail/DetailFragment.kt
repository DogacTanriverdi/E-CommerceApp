package com.dogactanriverdi.e_commerceapp.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.loadImage
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
import com.dogactanriverdi.e_commerceapp.databinding.FragmentDetailBinding
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToCartState
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.DetailState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailState = viewModel.detailState
        val addToFavoritesState = viewModel.addToFavoritesState
        val addToCartState = viewModel.addToCartState

        with(binding) {

            ibBack.setOnClickListener {
                findNavController().navigateUp()
            }

            lifecycleScope.launch {
                val userId = readUserId(requireContext(), DATASTORE_USER_ID_KEY)
                userId?.let {

                    ibAddFavorite.setOnClickListener {
                        viewModel.addToFavorites(
                            AddToFavoritesBody(
                                args.productId,
                                userId
                            )
                        )
                    }

                    tvAddToCart.setOnClickListener {
                        viewModel.addToCart(
                            AddToCartBody(
                                args.productId,
                                userId
                            )
                        )
                    }
                }
            }
        }

        viewModel.getProductDetail(args.productId)

        observeProductDetail(detailState)
        observeAddToFavorites(addToFavoritesState)
        observeAddToCart(addToCartState)
    }

    private fun observeProductDetail(state: StateFlow<DetailState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                state.collect { state ->

                    if (state.isLoading) {
                        tvError.gone()
                        nestedScrollView.gone()
                        ibBack.gone()
                        ibAddFavorite.gone()
                        bottomLayout.gone()
                        progressBar.visible()
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visible()
                        tvError.text = state.error
                        ibBack.visible()
                        nestedScrollView.gone()
                        bottomLayout.gone()
                        progressBar.gone()
                    }

                    state.detail?.let { detail ->
                        tvError.gone()
                        progressBar.gone()
                        nestedScrollView.visible()
                        bottomLayout.visible()
                        ibBack.visible()
                        ibAddFavorite.visible()

                        detail.product.let { response ->
                            ivProductImage.loadImage(response.imageOne)
                            tvTitle.text = response.title
                            tvCategory.text = response.category
                            tvDescription.text = response.description
                            if (response.saleState) {
                                tvPrice.text = response.salePrice.toString()
                            } else {
                                tvPrice.text = response.price.toString()
                            }
                            tvInStock.text = "${getString(R.string.in_stock)} ${response.count}"
                            tvRate.text = response.rate.toString()
                        }
                    }
                }
            }
        }
    }

    private fun observeAddToFavorites(state: StateFlow<AddToFavoritesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                state.collect { state ->

                    if (state.isLoading) {
                        ibAddFavorite.gone()
                        pbFavorites.visible()
                    }

                    if (state.error.isNotBlank()) {
                        ibAddFavorite.visible()
                        pbFavorites.gone()
                        Snackbar.make(requireView(), state.error, Snackbar.LENGTH_SHORT).show()
                    }

                    state.addToFavorites?.let { response ->
                        ibAddFavorite.visible()
                        pbFavorites.gone()
                        Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeAddToCart(state: StateFlow<AddToCartState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                state.collect { state ->

                    if (state.isLoading) {
                        tvAddToCart.gone()
                        pbAddToCart.visible()
                    }

                    if (state.error.isNotBlank()) {
                        tvAddToCart.visible()
                        pbAddToCart.gone()
                        Snackbar.make(requireView(), state.error, Snackbar.LENGTH_SHORT).show()
                    }

                    state.addToCart?.let { response ->
                        tvAddToCart.visible()
                        pbAddToCart.gone()
                        if (response.status == 400) {
                            Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT)
                                .show()
                        } else if (response.status == 200) {
                            Snackbar.make(
                                requireView(),
                                response.message,
                                Snackbar.LENGTH_LONG
                            ).setAction(getString(R.string.go_to_cart)) {
                                val action =
                                    DetailFragmentDirections.actionDetailFragmentToCartFragment()
                                findNavController().navigate(action)
                            }.show()
                        }
                    }
                }
            }
        }
    }
}