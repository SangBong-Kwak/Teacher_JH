package com.ilance.google_news_with_retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

object GoogleNewsServiceFactory {
    private const val HTTP_TIMEOUT_READ = 20L
    private const val HTTP_TIMEOUT_CONNECT = 10L

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        appendHeaderInterceptor: Interceptor
    ) = OkHttpClient.Builder()
//        .connectTimeout(HTTP_TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
//        .readTimeout(HTTP_TIMEOUT_READ, TimeUnit.MILLISECONDS)
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(appendHeaderInterceptor)
        .build()

    private fun makeLoggingInterceptor(debug: Boolean): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (debug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    private fun makeAppendHeaderInterceptor() = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
//            newRequest.addHeader("Version", "v1.0")
        chain.proceed(newRequest.build())
    }

    fun makeNewsService(debug: Boolean, baseUrl: String): GoogleNewsService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(debug),
            makeAppendHeaderInterceptor()
        )
        return makeNewsService(
            baseUrl,
            okHttpClient
        )
    }

    private fun makeNewsService(baseUrl: String, okHttpClient: OkHttpClient): GoogleNewsService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(SimpleXmlConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GoogleNewsService::class.java)
    }
}