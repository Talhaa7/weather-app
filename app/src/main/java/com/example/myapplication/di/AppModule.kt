package com.example.myapplication.di

import com.example.myapplication.data.RepositoryImpl
import com.example.myapplication.data.WebAPI
import com.example.myapplication.domain.Repository
import com.example.myapplication.utils.NoConnectionInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(noConnectionInterceptor: NoConnectionInterceptor) =
        OkHttpClient.Builder().addInterceptor(noConnectionInterceptor).build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideWebAPI(moshi: Moshi, okHttpClient: OkHttpClient): WebAPI =
        Retrofit.Builder()
            .baseUrl("https://www.metaweather.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(WebAPI::class.java)

    @Provides
    @Singleton
    fun provideRepository(webAPI: WebAPI) : Repository {
        return RepositoryImpl(webAPI)
    }
}