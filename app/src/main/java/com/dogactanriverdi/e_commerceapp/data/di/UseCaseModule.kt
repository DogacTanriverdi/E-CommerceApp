package com.dogactanriverdi.e_commerceapp.data.di

import com.dogactanriverdi.e_commerceapp.domain.repo.AddressRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.AuthRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.CartRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.CategoryRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.DetailRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.FavoriteRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.ProductRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.UserRepository
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.AddAddressUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.ClearAddressesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.DeleteFromAddressesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.GetAddressesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.auth.SignInUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.auth.SignUpUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.AddToCartUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.ClearCartUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.DeleteFromCartUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.GetCartProductsUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.category.GetCategoriesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.detail.GetProductDetailUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.AddToFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.ClearFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.DeleteFromFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.GetFavoriteCountUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.GetFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.GetProductsByCategoryUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.GetProductsUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.GetSaleProductsUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.SearchProductUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.user.ChangePasswordUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.user.EditProfileUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.user.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideAddAddressUseCase(
        repo: AddressRepository
    ): AddAddressUseCase {
        return AddAddressUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideClearAddressesUseCase(
        repo: AddressRepository
    ): ClearAddressesUseCase {
        return ClearAddressesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideDeleteFromAddressesUseCase(
        repo: AddressRepository
    ): DeleteFromAddressesUseCase {
        return DeleteFromAddressesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideAddressesUseCase(
        repo: AddressRepository
    ): GetAddressesUseCase {
        return GetAddressesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideSignInUseCase(
        repo: AuthRepository
    ): SignInUseCase {
        return SignInUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        repo: AuthRepository
    ): SignUpUseCase {
        return SignUpUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideAddToCartUseCase(
        repo: CartRepository
    ): AddToCartUseCase {
        return AddToCartUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideClearCartUseCase(
        repo: CartRepository
    ): ClearCartUseCase {
        return ClearCartUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideDeleteFromCartUseCase(
        repo: CartRepository
    ): DeleteFromCartUseCase {
        return DeleteFromCartUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetCartProductsUseCase(
        repo: CartRepository
    ): GetCartProductsUseCase {
        return GetCartProductsUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetCategoriesUseCase(
        repo: CategoryRepository
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetProductDetailUseCase(
        repo: DetailRepository
    ): GetProductDetailUseCase {
        return GetProductDetailUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideAddToFavoritesUseCase(
        repo: FavoriteRepository
    ): AddToFavoritesUseCase {
        return AddToFavoritesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideClearFavoritesUseCase(
        repo: FavoriteRepository
    ): ClearFavoritesUseCase {
        return ClearFavoritesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideDeleteFromFavoritesUseCase(
        repo: FavoriteRepository
    ): DeleteFromFavoritesUseCase {
        return DeleteFromFavoritesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetFavoriteCountUseCase(
        repo: FavoriteRepository
    ): GetFavoriteCountUseCase {
        return GetFavoriteCountUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetFavoritesUseCase(
        repo: FavoriteRepository
    ): GetFavoritesUseCase {
        return GetFavoritesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetProductsByCategoryUseCase(
        repo: ProductRepository
    ): GetProductsByCategoryUseCase {
        return GetProductsByCategoryUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetProductsUseCase(
        repo: ProductRepository
    ): GetProductsUseCase {
        return GetProductsUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetSaleProductsUseCase(
        repo: ProductRepository
    ): GetSaleProductsUseCase {
        return GetSaleProductsUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideSearchProductUseCase(
        repo: ProductRepository
    ): SearchProductUseCase {
        return SearchProductUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideChangePasswordUseCase(
        repo: UserRepository
    ): ChangePasswordUseCase {
        return ChangePasswordUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideEditProfileUseCase(
        repo: UserRepository
    ): EditProfileUseCase {
        return EditProfileUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetUserUseCase(
        repo: UserRepository
    ): GetUserUseCase {
        return GetUserUseCase(repo)
    }
}