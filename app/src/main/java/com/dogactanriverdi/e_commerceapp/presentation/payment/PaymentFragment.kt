package com.dogactanriverdi.e_commerceapp.presentation.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dogactanriverdi.e_commerceapp.R
import com.dogactanriverdi.e_commerceapp.common.Constants
import com.dogactanriverdi.e_commerceapp.common.gone
import com.dogactanriverdi.e_commerceapp.common.readUserId
import com.dogactanriverdi.e_commerceapp.common.viewBinding
import com.dogactanriverdi.e_commerceapp.common.visible
import com.dogactanriverdi.e_commerceapp.databinding.FragmentPaymentBinding
import com.dogactanriverdi.e_commerceapp.domain.model.cart.ClearCartBody
import com.dogactanriverdi.e_commerceapp.presentation.cart.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    private val cartViewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            ibBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnCompletePayment.setOnClickListener {
                val cardNumber = etCardNumber.text.toString()
                val nameOnTheCard = etNameOnTheCard.text.toString()
                val expirationDate = etExpirationDate.text.toString()
                val cvc = etCvc.text.toString()

                when {
                    cardNumber.isEmpty() || cardNumber.length != 16 -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.card_number_is_blank_or_incorrect),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    nameOnTheCard.isEmpty() -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.name_on_the_card_is_blank), Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    expirationDate.isEmpty() || expirationDate.length != 4 -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.expiration_date_is_blank_or_incorrect),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    cvc.isEmpty() || cvc.length != 3 -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.cvc_is_blank_or_incorrect_please_write_like_445),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        lifecycleScope.launch {
                            val userId =
                                readUserId(requireContext(), Constants.DATASTORE_USER_ID_KEY)
                            userId?.let {
                                paymentLayout.gone()
                                ivPaymentCompleted.visible()
                                cartViewModel.clearCart(ClearCartBody(userId))
                            }
                        }
                    }
                }
            }
        }
    }
}