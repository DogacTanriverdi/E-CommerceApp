package com.dogactanriverdi.e_commerceapp.presentation.search.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.loadImage
import com.dogactanriverdi.e_commerceapp.databinding.ProductItemBinding
import com.dogactanriverdi.e_commerceapp.domain.model.product.Product

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(
        private val binding: ProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            with(binding) {
                ivProductImage.loadImage(product.imageOne)
                tvTitle.text = product.title
                tvRate.text = product.rate.toString()
                if (!product.saleState) {
                    tvPrice.text = "₺${product.price}"
                } else {
                    tvSalePrice.visibility = View.VISIBLE
                    tvSalePrice.text = "₺${product.salePrice}"
                    tvPrice.text = "₺${product.price}"
                    tvPrice.setTextColor(ContextCompat.getColor(tvPrice.context, R.color.gray))
                    tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    private var products: List<Product>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = products[position]
        with(holder) {
            bind(product)
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(product)
                }
            }
        }
    }

    private var onItemClickListener: ((Product) -> Unit)? = null

    fun setOnItemClickListener(listener: (Product) -> Unit) {
        onItemClickListener = listener
    }
}