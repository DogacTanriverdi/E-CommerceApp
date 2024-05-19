package com.dogactanriverdi.e_commerceapp.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentCartBinding
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.ClearCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.DeleteFromCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.DeleteFromFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.EditProfileBody
import com.dogactanriverdi.e_commerceapp.presentation.cart.adapter.CartAdapter
import com.dogactanriverdi.e_commerceapp.presentation.cart.state.CartProductsState
import com.dogactanriverdi.e_commerceapp.presentation.cart.state.ClearCartState
import com.dogactanriverdi.e_commerceapp.presentation.profile.ProfileFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel: CartViewModel by viewModels()

    private val cartAdapter by lazy { CartAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartProductsState = viewModel.cartProductsState
        val clearCartState = viewModel.clearCartState

        lifecycleScope.launch {
            val userId = readUserId(requireContext(), Constants.DATASTORE_USER_ID_KEY)
            userId?.let {
                with(binding) {

                    viewModel.cartProducts(userId)

                    ibClearCart.setOnClickListener {
                        viewModel.clearCart(
                            ClearCartBody(userId)
                        )
                    }

                    btnGoToPayment.setOnClickListener {
                        // Navigate to payment fragment
                    }

                    ibBack.setOnClickListener {
                        findNavController().navigateUp()
                    }
                }
            }
        }

        setupCartAdapter()

        observeCartProduct(cartProductsState)
        observeClearCart(clearCartState)

        setSwipeToDelete()
    }

    private fun setupCartAdapter() {
        binding.rvProducts.adapter = cartAdapter

        cartAdapter.setOnItemClickListener { product ->
            val action = CartFragmentDirections.actionCartFragmentToDetailFragment(product.id)
            findNavController().navigate(action)
        }
    }

    private fun observeCartProduct(state: StateFlow<CartProductsState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        progressBar.visibility = View.VISIBLE
                        rvProducts.visibility = View.GONE
                        tvError.visibility = View.GONE
                        btnGoToPayment.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        progressBar.visibility = View.GONE
                        rvProducts.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                        btnGoToPayment.visibility = View.GONE
                        tvError.text = state.error
                    }

                    state.products?.let { products ->
                        progressBar.visibility = View.GONE
                        rvProducts.visibility = View.VISIBLE
                        tvError.visibility = View.GONE
                        btnGoToPayment.visibility = View.VISIBLE
                        cartAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }

    private fun observeClearCart(state: StateFlow<ClearCartState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        pbClearCart.visibility = View.VISIBLE
                        ibClearCart.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        pbClearCart.visibility = View.GONE
                        ibClearCart.visibility = View.VISIBLE
                        Snackbar.make(
                            requireView(),
                            state.error,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                    state.clearCart?.let { response ->
                        pbClearCart.visibility = View.GONE
                        ibClearCart.visibility = View.VISIBLE
                        Snackbar.make(
                            requireView(),
                            response.message,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun setSwipeToDelete() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val currentProduct = cartAdapter.products[itemPosition]

                lifecycleScope.launch {
                    val userId = readUserId(requireContext(), Constants.DATASTORE_USER_ID_KEY)
                    userId?.let {
                        viewModel.deleteFomCart(
                            DeleteFromCartBody(
                                currentProduct.id,
                                userId
                            )
                        )

                        Snackbar.make(
                            requireView(),
                            getString(R.string.product_successfully_deleted),
                            Snackbar.LENGTH_LONG
                        ).setAction(getString(R.string.undo)) {
                            viewModel.addToCart(
                                AddToCartBody(
                                    currentProduct.id,
                                    userId
                                )
                            )
                        }.show()
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.rvProducts)
    }
}