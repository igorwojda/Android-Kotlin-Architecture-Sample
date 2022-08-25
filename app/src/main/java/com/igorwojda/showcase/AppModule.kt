package com.igorwojda.showcase

import com.igorwojda.showcase.app.data.network.AuthenticationInterceptor
import com.igorwojda.showcase.app.data.network.UserAgentInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

internal const val MODULE_NAME = "App"

val appModule = module {

    single { AuthenticationInterceptor(BuildConfig.GRADLE_API_TOKEN) }

    singleOf(::UserAgentInterceptor)

    single {
        HttpLoggingInterceptor { message ->
            Timber.d("HttpLog: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<AuthenticationInterceptor>())
            .addInterceptor(get<UserAgentInterceptor>())
            .build()
    }



    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.GRADLE_API_BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
