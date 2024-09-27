package com.zaus_app.playlistmaker.domain.di.modules

import com.zaus_app.playlistmaker.data.api.ApiConstants
import com.zaus_app.playlistmaker.data.api.TrackApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get<OkHttpClient>())
            .build()
    }

    single<GsonConverterFactory>{
        GsonConverterFactory.create()
    }

    single<TrackApi> {
        get<Retrofit>().create(TrackApi::class.java)
    }
}

