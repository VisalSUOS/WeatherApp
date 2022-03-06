package suos.visal.mykotlinclassone.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RemoteServiceManager {
    private val baseUrl = "https://api.openweathermap.org"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()

    fun <T> instance(service: Class<T>) : T = retrofit.create(service)

    val retrofitService : WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}