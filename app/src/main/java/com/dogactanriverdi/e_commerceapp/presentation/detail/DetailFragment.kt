package com.dogactanriverdi.e_commerceapp.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.loadImage
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentDetailBinding
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToCartState
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.DetailState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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

    private fun observeProductDetail(detailState: StateFlow<DetailState>) {
        viewLifecycleOwner.lifecycleScope.launch {

            with(binding) {

                detailState.collect { state ->

                    if (state.isLoading) {
                        tvError.visibility = View.GONE
                        nestedScrollView.visibility = View.GONE
                        ibBack.visibility = View.GONE
                        ibAddFavorite.visibility = View.GONE
                        bottomLayout.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visibility = View.VISIBLE
                        tvError.text = state.error
                        ibBack.visibility = View.VISIBLE
                        nestedScrollView.visibility = View.GONE
                        bottomLayout.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }

                    state.detail?.let { detail ->
                        tvError.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        nestedScrollView.visibility = View.VISIBLE
                        bottomLayout.visibility = View.VISIBLE
                        ibBack.visibility = View.VISIBLE
                        ibAddFavorite.visibility = View.VISIBLE

                        detail.product.let { product ->
                            ivProductImage.loadImage(product.imageOne)
                            tvTitle.text = product.title
                            tvCategory.text = product.category
                            tvDescription.text = product.description
                            if (product.saleState) {
                                tvPrice.text = product.salePrice.toString()
                            } else {
                                tvPrice.text = product.price.toString()
                            }
                            tvInStock.text = "${getString(R.string.in_stock)} ${product.count}"
                            tvRate.text = product.rate.toString()
                        }
                    }
                }
            }
        }
    }

    private fun observeAddToFavorites(addToFavoritesState: StateFlow<AddToFavoritesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                addToFavoritesState.collect { state ->

                    if (state.isLoading) {
                        ibAddFavorite.visibility = View.GONE
                        pbFavorites.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        ibAddFavorite.visibility = View.VISIBLE
                        pbFavorites.visibility = View.GONE
                        val snackbar =
                            Snackbar.make(requireView(), state.error, Snackbar.LENGTH_SHORT)
                        snackbar.show()
                    }

                    state.addToFavorites?.let { response ->
                        ibAddFavorite.visibility = View.VISIBLE
                        pbFavorites.visibility = View.GONE
                        val snackbar =
                            Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT)
                        snackbar.show()
                    }
                }
            }
        }
    }

    private fun observeAddToCart(addToCartState: StateFlow<AddToCartState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                addToCartState.collect { state ->

                    if (state.isLoading) {
                        tvAddToCart.visibility = View.GONE
                        pbAddToCart.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        tvAddToCart.visibility = View.VISIBLE
                        pbAddToCart.visibility = View.GONE
                        Snackbar.make(requireView(), state.error, Snackbar.LENGTH_SHORT).show()
                    }

                    state.addToCart?.let { response ->
                        tvAddToCart.visibility = View.VISIBLE
                        pbAddToCart.visibility = View.GONE
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