  package com.example.myapplication.home

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.Search
import com.example.myapplication.ui.Setting
import com.example.myapplication.model.local.localrepositryImp
import com.example.myapplication.model.local.weatherDatabase
import com.example.myapplication.model.entity.Modelweather
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
      lateinit var localrepositryImpp: localrepositryImp
//      lateinit var  weatherdescription:String;
    private var layoutManager:RecyclerView.LayoutManager?=null
    private lateinit var recyclerView:RecyclerView
    val city:String="alexandria"
    val API:String="8789ec6f7fe211cc4333fb849ff4a8ff"
   // private var adapter:RecyclerView.Adapter<recycleadapter.ViewHolder>?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //recyclerView=findViewById(R.id.recyv);
       var db=weatherDatabase.getInstance(this)
        layoutManager=LinearLayoutManager(this)
       // recyclerView.layoutManager=layoutManager
        //adapter=recycleadapter()
       // recyclerView.adapter=adapter


         val drawerLayout:DrawerLayout=findViewById(R.id.drawer)
        val navview:NavigationView=findViewById(R.id.navview)
        toggle= ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
       val intent = Intent(this, Setting::class.java).apply {
//           putExtra("weatherdescription", weatherdescription)
       }
       val searchintent = Intent(this, Search::class.java).apply {
//           putExtra("weatherdescription", weatherdescription)
       }
       startActivity(intent);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navview.setNavigationItemSelectedListener {
            val intent = Intent(this, Setting::class.java)
            when(it.itemId)
            {

                R.id.home ->Toast.makeText(applicationContext,"home click",Toast.LENGTH_LONG).show();
                R.id.setting ->startActivity(intent)
                R.id.search ->startActivity(searchintent)

            }
            true
        }
       weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE

        }
        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                //  https://api.openweathermap.org/data/2.5/weather?q=$dhaka,bd&units=metric&appid=8789ec6f7fe211cc4333fb849ff4a8ff
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=alexandria&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
              val  weatherdescription = weather.getString("description")
                val address = jsonObj.getString("name")+", "+sys.getString("country")
                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text =  updatedAtText
                findViewById<TextView>(R.id.status).text = weatherdescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp

                findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                    Date(sunrise*1000)
                )

                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity
                /* Views populated, Hiding the loader, Showing the main design */
                var db=weatherDatabase.getInstance(applicationContext)
               localrepositryImpp=localrepositryImp(db )
                GlobalScope.launch (Dispatchers.IO)
                {
                    localrepositryImpp.insertOrUpdateWeather(
                        Modelweather(address ,temp,tempMin,tempMax,pressure,humidity,weatherdescription,windSpeed)
                    )
                    //***************
                }
                    findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        }
    }
}