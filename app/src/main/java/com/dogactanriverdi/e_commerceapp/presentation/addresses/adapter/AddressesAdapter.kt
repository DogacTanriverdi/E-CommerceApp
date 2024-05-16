package com.dogactanriverdi.e_commerceapp.presentation.addresses.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dogactanriverdi.e_commerceapp.databinding.AddressesItemBinding
import com.dogactanriverdi.e_commerceapp.domain.model.address.Address

class AddressesAdapter : RecyclerView.Adapter<AddressesAdapter.AddressesViewHolder>() {

    class AddressesViewHolder(
        private val binding: AddressesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address) {
            with(binding) {
                tvAddressTitle.text = address.title
                tvAddressDetail.text = address.address
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Address>() {

        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }
    }

    val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    private var addresses: List<Address>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesViewHolder {
        val binding = AddressesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AddressesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

    override fun onBindViewHolder(holder: AddressesViewHolder, position: Int) {
        val address = addresses[position]
        holder.bind(address)
    }
}