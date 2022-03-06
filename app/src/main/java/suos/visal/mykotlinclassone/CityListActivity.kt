package suos.visal.mykotlinclassone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import suos.visal.mykotlinclassone.databinding.ActivityCityListBinding
import suos.visal.mykotlinclassone.intent.CityListIntent

class CityListActivity : AppCompatActivity() {
    private val cities = listOf(
        1821306 to "Phnom Penh",
        1821310 to "Battambang",
        1821453 to "Tuol Tumpung",
        1821935 to "Ta Khmau",
        1821939 to "Takeo",
        1821992 to "Svay Rieng",
        1822028 to "Stung Treng",
        1822207 to "Sisophon",
        1822213 to "Siem Reap",
        1822227 to "Sen Monorom",
        1822331 to "Samraong",
        1822449 to "Ratanakiri",
        1822610 to "Prey Veng",
        1822676 to "Preah Vihear",
        1822768 to "Pursat",
        1822906 to "Phumi Veal Sre",
        1825049 to "Tuek Vil",
        1825664 to "Phumi Prey Snuol",
        1826509 to "Phumi Phlov"
    )
    private lateinit var binding: ActivityCityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCityList.apply {
            adapter = CityAdapter(cities, onItemClick = {
                setResult(RESULT_OK, CityListIntent(this@CityListActivity, it.first))
                finish()
            })
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    class CityAdapter(private val listCities : List<Pair<Int, String>>, val onItemClick : (Pair<Int, String>) -> Unit) : RecyclerView.Adapter<CityAdapter.CityVH>() {

        class CityVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cityNameText: TextView = itemView.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityVH {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return CityVH(view)
        }

        override fun onBindViewHolder(holder: CityVH, position: Int) {
            val city = listCities[position]
            holder.cityNameText.text = city.second
            holder.cityNameText.setOnClickListener { onItemClick(city) }
        }

        override fun getItemCount() = listCities.size
    }
}