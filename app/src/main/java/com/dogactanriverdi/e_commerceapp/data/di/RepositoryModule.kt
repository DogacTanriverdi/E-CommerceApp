package com.dogactanriverdi.e_commerceapp.data.di

import com.dogactanriverdi.e_commerceapp.data.repo.AddressRepositoryImpl
import com.dogactanriverdi.e_commerceapp.data.repo.AuthRepositoryImpl
import com.dogactanriverdi.e_commerceapp.data.repo.CartRepositoryImpl
import com.dogactanriverdi.e_commerceapp.data.repo.CategoryRepositoryImpl
import com.dogactanriverdi.e_commerceapp.data.repo.DetailRepositoryImpl
import com.dogactanriverdi.e_commerceapp.data.repo.FavoriteRepositoryImpl
import com.dogactanriverdi.e_commerceapp.data.repo.ProductRepositoryImpl
import com.dogactanriverdi.e_commerceapp.data.repo.UserRepositoryImpl
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.AddressService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.AuthService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.CartService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.CategoryService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.DetailService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.FavoriteService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.ProductService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.UserService
import com.dogactanriverdi.e_commerceapp.domain.repo.AddressRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.AuthRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.CartRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.CategoryRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.DetailRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.FavoriteRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.ProductRepository
import com.dogactanriverdi.e_commerceapp.domain.repo.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAddressRepository(
        service: AddressService
    ): AddressRepository {
        return AddressRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        service: AuthService
    ): AuthRepository {
        return AuthRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideCartRepository(
        service: CartService
    ): CartRepository {
        return CartRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        service: CategoryService
    ): CategoryRepository {
        return CategoryRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideDetailsRepository(
        service: DetailService
    ): DetailRepository {
        return DetailRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(
        service: FavoriteService
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideProductRepository(
        service: ProductService
    ): ProductRepository {
        return ProductRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        service: UserService
    ): UserRepository {
        return UserRepositoryImpl(service)
    }
}