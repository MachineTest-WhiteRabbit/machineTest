package com.example.machinetest.ui.rest

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiClient {
    @GET("5d565297300000680030a986")
    fun getDataFromURL() : Single<Response<JsonArray>>


    companion object {
        const val BASE_URL = "http://www.mocky.io/v2/"
        fun create(): ApiClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            val client = OkHttpClient.Builder().addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val original = chain.request()

                    val requestBuilder: Request.Builder = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                    val request = requestBuilder.build()
                    return chain.proceed(request)
                }

            }).connectTimeout(100000, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
            return retrofit.create(ApiClient::class.java)
        }
    }
}