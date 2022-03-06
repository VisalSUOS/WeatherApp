package suos.visal.mykotlinclassone.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import suos.visal.mykotlinclassone.data.Data

interface WeatherService {
    @GET("/data/2.5/weather?APPID=4495ed6cc4e51a609219b5711bb687b4&mode=json&units=metric")
    fun getWeather(@Query("id") id: Int) : Call<Data>
}