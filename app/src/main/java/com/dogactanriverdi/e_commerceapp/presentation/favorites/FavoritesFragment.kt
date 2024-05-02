package com.dogactanriverdi.e_commerceapp.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.databinding.FragmentFavoritesBinding
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.ClearFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.DeleteFromFavoritesBody
import com.dogactanriverdi.e_commerceapp.presentation.favorites.adapter.FavoritesAdapter
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.ClearFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.FavoritesState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel: FavoritesViewModel by viewModels()

    private val favoritesAdapter by lazy { FavoritesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoritesState = viewModel.favoritesState
        val clearFavoritesState = viewModel.clearFavoritesState

        lifecycleScope.launch {
            val userId = readUserId(requireContext(), DATASTORE_USER_ID_KEY)
            userId?.let {
                viewModel.getFavorites(userId)

                binding.ibClearFavorites.setOnClickListener {
                    viewModel.clearFavorites(
                        ClearFavoritesBody(userId)
                    )
                }
            }
        }

        setupFavoritesAdapter()
        observeFavorites(favoritesState)

        setSwipeToDelete()
        observeClearFavorites(clearFavoritesState)
    }

    private fun setupFavoritesAdapter() {
        binding.rvFavorites.adapter = favoritesAdapter

        favoritesAdapter.setOnItemClickListener { product ->
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(
                product.id
            )
            findNavController().navigate(action)
        }
    }

    private fun observeFavorites(favoritesState: StateFlow<FavoritesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            favoritesState.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        progressBar.visibility = View.VISIBLE
                        rvFavorites.visibility = View.GONE
                        tvError.visibility = View.GONE
                        ibClearFavorites.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        progressBar.visibility = View.GONE
                        rvFavorites.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                        tvError.text = state.error
                        ibClearFavorites.visibility = View.GONE
                    }

                    state.favorites?.let { products ->
                        progressBar.visibility = View.GONE
                        tvError.visibility = View.GONE
                        rvFavorites.visibility = View.VISIBLE
                        ibClearFavorites.visibility = View.VISIBLE
                        favoritesAdapter.recyclerListDiffer.submitList(products.products)
                    }
                }
            }
        }
    }

    private fun observeClearFavorites(clearFavoritesState: StateFlow<ClearFavoritesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            clearFavoritesState.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        pbClearFavorites.visibility = View.VISIBLE
                        ibClearFavorites.visibility = View.GONE
                    }

                    if (state.error.isNotBlank()) {
                        pbClearFavorites.visibility = View.GONE
                        ibClearFavorites.visibility = View.GONE
                        val snackbar =
                            Snackbar.make(requireView(), state.error, Snackbar.LENGTH_SHORT)
                        snackbar.show()
                    }

                    state.clearFavorites?.let { response ->
                        pbClearFavorites.visibility = View.GONE
                        ibClearFavorites.visibility = View.VISIBLE
                        val snackbar =
                            Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT)
                        snackbar.show()
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
                val currentProduct = favoritesAdapter.products[itemPosition]

                lifecycleScope.launch {
                    val userId = readUserId(requireContext(), DATASTORE_USER_ID_KEY)
                    userId?.let {
                        viewModel.deleteFromFavorites(
                            DeleteFromFavoritesBody(
                                currentProduct.id,
                                userId
                            )
                        )

                        Snackbar.make(
                            requireView(),
                            getString(R.string.product_successfully_deleted),
                            Snackbar.LENGTH_LONG
                        ).setAction(getString(R.string.undo)) {
                            viewModel.addToFavorites(
                                AddToFavoritesBody(
                                    currentProduct.id,
                                    userId
                                )
                            )
                        }.show()
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.rvFavorites)
    }
}