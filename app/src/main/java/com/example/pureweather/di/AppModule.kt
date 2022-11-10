package com.example.pureweather.di

import android.content.Context
import android.content.SharedPreferences
import com.example.pureweather.BuildConfig
import com.example.pureweather.BuildConfig.BASE_URL
import com.example.pureweather.domain.LocalRepoImpl
import com.example.pureweather.domain.WeatherInteractor
import com.example.pureweather.domain.WeatherRepoImpl
import com.example.pureweather.data.repository.LocalRepository
import com.example.pureweather.data.repository.WeatherRepository
import com.example.pureweather.data.network.ApiService
import com.example.pureweather.data.network.AuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiPhoto(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(sharedPreferences: SharedPreferences): LocalRepository {
        return LocalRepoImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: ApiService): WeatherRepository {
        return WeatherRepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideWeatherInteractor(
        localRepo: LocalRepository,
        weatherRepo: WeatherRepository
    ): WeatherInteractor {
        return WeatherInteractor(localRepo, weatherRepo)
    }
}