package com.example.vismamusic.di

import android.content.Context
import com.example.vismamusic.data.SongsRepositoryImpl
import com.example.vismamusic.data.local.SharedPrefs
import com.example.vismamusic.data.local.TemporaryStorage
import com.example.vismamusic.data.provider.InputStreamProvider
import com.example.vismamusic.data.remote.SongsApi
import com.example.vismamusic.domain.repository.SongsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.mirrajabi.okhttpjsonmock.OkHttpMockInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSongsApi(@ApplicationContext appContext: Context): SongsApi {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(OkHttpMockInterceptor(InputStreamProvider(appContext), 0))
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://example.com")
            .client(okHttpClient)
            .build()
            .create(SongsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext appContext: Context): SharedPrefs = SharedPrefs(appContext)

    @Provides
    @Singleton
    fun provideTemporaryStorage(): TemporaryStorage = TemporaryStorage()

    @Provides
    @Singleton
    fun provideSongsRepository(api: SongsApi): SongsRepository = SongsRepositoryImpl(api)
}