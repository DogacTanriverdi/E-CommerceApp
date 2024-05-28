package com.dogactanriverdi.e_commerceapp.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_USER_ID_KEY
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
import com.dogactanriverdi.e_commerceapp.databinding.FragmentFavoritesBinding
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.ClearFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.DeleteFromFavoritesBody
import com.dogactanriverdi.e_commerceapp.presentation.favorites.adapter.FavoritesAdapter
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.AddToFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.ClearFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.DeleteFromFavoritesState
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

        val favoritesState = viewModel.favoritesState
        val deleteFromFavoritesState = viewModel.deleteFromFavoritesState
        val addToFavoritesState = viewModel.addToFavoritesState
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
        observeDeleteFromFavorites(deleteFromFavoritesState)
        observeAddToFavorites(addToFavoritesState)
        observeClearFavorites(clearFavoritesState)

        setSwipeToDelete()
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

    private fun observeFavorites(state: StateFlow<FavoritesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        progressBar.visible()
                        rvFavorites.gone()
                        tvError.gone()
                        ibClearFavorites.gone()
                    }

                    if (state.error.isNotBlank()) {
                        progressBar.gone()
                        rvFavorites.gone()
                        tvError.visible()
                        tvError.text = state.error
                        ibClearFavorites.gone()
                    }

                    state.favorites?.let { response ->
                        progressBar.gone()
                        tvError.gone()
                        rvFavorites.visible()
                        ibClearFavorites.visible()
                        favoritesAdapter.recyclerListDiffer.submitList(response.products)
                    }
                }
            }
        }
    }

    private fun observeDeleteFromFavorites(state: StateFlow<DeleteFromFavoritesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        pbAddAndDeleteFromFavorites.visible()
                    }

                    if (state.error.isNotBlank()) {
                        pbAddAndDeleteFromFavorites.gone()
                        Snackbar.make(requireView(), state.error, Snackbar.LENGTH_SHORT).show()
                    }

                    state.deleteFromFavorites?.let { _ ->
                        pbAddAndDeleteFromFavorites.gone()
                    }
                }
            }
        }
    }

    private fun observeAddToFavorites(state: StateFlow<AddToFavoritesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        pbAddAndDeleteFromFavorites.visible()
                    }

                    if (state.error.isNotBlank()) {
                        pbAddAndDeleteFromFavorites.gone()
                        Snackbar.make(requireView(), state.error, Snackbar.LENGTH_SHORT).show()
                    }

                    state.addToFavorites?.let { response ->
                        pbAddAndDeleteFromFavorites.gone()
                        Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeClearFavorites(state: StateFlow<ClearFavoritesState>) {
        viewLifecycleOwner.lifecycleScope.launch {
            state.collect { state ->
                with(binding) {

                    if (state.isLoading) {
                        pbClearFavorites.visible()
                        ibClearFavorites.gone()
                    }

                    if (state.error.isNotBlank()) {
                        pbClearFavorites.gone()
                        ibClearFavorites.gone()
                        Snackbar.make(requireView(), state.error, Snackbar.LENGTH_SHORT).show()
                    }

                    state.clearFavorites?.let { response ->
                        pbClearFavorites.gone()
                        ibClearFavorites.visible()
                        Snackbar.make(requireView(), response.message, Snackbar.LENGTH_SHORT).show()
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