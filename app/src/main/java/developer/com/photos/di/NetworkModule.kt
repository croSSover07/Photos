package developer.com.photos.di

import com.google.gson.Gson
import developer.com.core.data.net.gson
import developer.com.photos.BuildConfig
import developer.com.photos.data.net.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module
import java.util.concurrent.TimeUnit

class NetworkModule : Module() {
    init {
        bind(Gson::class.java).toInstance(gson)

        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val rеquest = chain.request()
                chain.proceed(
                    rеquest.newBuilder()
                        .addHeader("Authorization" ,"Client-ID ${BuildConfig.ACCESS_KEY}")
                        .addHeader("Accept-Version", "v1")
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else HttpLoggingInterceptor.Level.NONE
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        bind(Api::class.java).toInstance(retrofit.create(Api::class.java))
    }
}
