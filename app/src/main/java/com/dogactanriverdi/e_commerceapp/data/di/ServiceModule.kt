package com.dogactanriverdi.e_commerceapp.data.di

import com.dogactanriverdi.e_commerceapp.common.Constants.BASE_URL
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.AddressService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.AuthService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.CartService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.CategoryService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.DetailService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.FavoriteService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.ProductService
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideAddressService(retrofit: Retrofit): AddressService {
        return retrofit.create<AddressService>()
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create<AuthService>()
    }

    @Singleton
    @Provides
    fun provideCartService(retrofit: Retrofit): CartService {
        return retrofit.create<CartService>()
    }

    @Singleton
    @Provides
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create<CategoryService>()
    }

    @Singleton
    @Provides
    fun provideDetailService(retrofit: Retrofit): DetailService {
        return retrofit.create<DetailService>()
    }

    @Singleton
    @Provides
    fun provideFavoriteService(retrofit: Retrofit): FavoriteService {
        return retrofit.create<FavoriteService>()
    }

    @Singleton
    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create<ProductService>()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create<UserService>()
    }
}