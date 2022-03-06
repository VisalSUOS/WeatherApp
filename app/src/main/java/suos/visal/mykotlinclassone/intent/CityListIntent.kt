package suos.visal.mykotlinclassone.intent

import android.content.Context
import android.content.Intent
import suos.visal.mykotlinclassone.CityListActivity

private const val ID_CITY_KEY = "ID_CITY_KEY"

class CityListIntent : Intent {

    constructor(packageContext: Context?) : super(packageContext, CityListActivity::class.java)
    constructor(packageContext: Context?, id: Int) : super(packageContext, CityListActivity::class.java) {
        putExtra(ID_CITY_KEY, id)
    }

    constructor(intent: Intent) : super(intent)

    fun getCityID() = this.getIntExtra(ID_CITY_KEY, 0)
}