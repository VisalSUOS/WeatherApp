package suos.visal.mykotlinclassone

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import suos.visal.mykotlinclassone.data.Data
import suos.visal.mykotlinclassone.databinding.ActivityMainBinding
import suos.visal.mykotlinclassone.intent.CityListIntent
import suos.visal.mykotlinclassone.network.RemoteServiceManager
import suos.visal.mykotlinclassone.network.WeatherService


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var cityID = 1821306

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            result.data?.let {
                CityListIntent(it).let { cityListIntent ->
                    val cityID = cityListIntent.getCityID()
                    if(cityID != 0){
                        this.cityID = cityID
                        fetchData(this.cityID)
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpItems()
        fetchData(cityID)
    }

    private fun setUpItems() {
        binding.btnRefresh.text = "Refresh"
        binding.btnRefresh.setOnClickListener {
            fetchData(cityID)
        }
        binding.tvLocation.setOnClickListener {
            resultLauncher.launch(CityListIntent(this))
        }
    }

    private fun fetchData(cityID: Int) {
        showLoading()
        RemoteServiceManager.instance(WeatherService::class.java).getWeather(cityID)
            .enqueue(object : Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    hideLoading()
                    val body = response.body()
                    mapGeneralInfo(body)
                    mapWeatherStatus(body)
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    hideLoading()
                    Log.i("onFailure: ", call.toString())
                }
            })
    }

    private fun showLoading() {
        binding.groupWeather.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.groupWeather.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun mapGeneralInfo(body: Data?) {
        binding.tvLocation.text = body?.name ?: "N/A"
        binding.tvTemperatureValue.text = body?.main?.temp?.toInt().toString()
        binding.tvWindSpeed.text = body?.wind?.speed.toString()
        binding.tvHumidity.text = body?.main?.humidity.toString()
        binding.tvCloudiness.text = body?.clouds?.all.toString()
    }

    private fun mapWeatherStatus(body: Data?) {
        body?.weather?.firstOrNull()?.let {
            val resId = when (it.id) {
                in 200..232 -> R.drawable.ic_thunderstorm_large
                in 300..321 -> R.drawable.ic_drizzle_large
                in 500..531 -> R.drawable.ic_rain_large
                in 600..622 -> R.drawable.ic_snow_large
                800 -> R.drawable.ic_day_clear_large
                801 -> R.drawable.ic_day_few_clouds_large
                802 -> R.drawable.ic_scattered_clouds_large
                803, 804 -> R.drawable.ic_broken_clouds_large
                701, 711, 721, 731, 741, 751, 761, 762 -> R.drawable.ic_fog_large
                781, 900 -> R.drawable.ic_tornado_large
                905 -> R.drawable.ic_windy_large
                906 -> R.drawable.ic_hail_large
                else -> R.mipmap.ic_launcher
            }
            binding.ivBig.setImageResource(resId)
        }
    }

    override fun onResume() {
        super.onResume()


    }
}
