package com.aqua_waterfliter.rickmorty.core.data.di

import com.aqua_waterfliter.rickmorty.core.data.api.CharacterApi
import com.aqua_waterfliter.rickmorty.core.data.api.interceptors.LoggingInterceptor
import com.aqua_waterfliter.rickmorty.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    // provide the base retrofit builder
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    // provide the http client fot the retrofit builder
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(5, TimeUnit.MINUTES) // write timeout
            .readTimeout(5, TimeUnit.MINUTES) // read timeout
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    // provide logging interceptor for logging api call results for debugging
    @Provides
    fun provideHttpLoggingInterceptor(
        loggingInterceptor: LoggingInterceptor,
    ): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideCharacterApi(builder: Retrofit.Builder): CharacterApi {
        return builder
            .build()
            .create(CharacterApi::class.java)
    }


}