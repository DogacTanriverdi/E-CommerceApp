package com.dogactanriverdi.e_commerceapp.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.loadImage
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentDetailBinding
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.DetailState
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

        viewModel.getProductDetail(args.productId)

        observeProductDetail(detailState)
    }

    private fun observeProductDetail(detailState: StateFlow<DetailState>) {
        viewLifecycleOwner.lifecycleScope.launch {

            with(binding) {

                detailState.collect { state ->

                    if (state.isLoading) {
                        tvError.visibility = View.GONE
                        nestedScrollView.visibility = View.GONE
                        bottomLayout.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    if (state.error.isNotBlank()) {
                        tvError.visibility = View.VISIBLE
                        tvError.text = state.error
                        nestedScrollView.visibility = View.GONE
                        bottomLayout.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }

                    state.detail?.let { detail ->
                        tvError.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        nestedScrollView.visibility = View.VISIBLE
                        bottomLayout.visibility = View.VISIBLE

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
                            tvAddToCart.setOnClickListener {
                                val action =
                                    DetailFragmentDirections.actionDetailFragmentToCartFragment()
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
        }
    }
}