package com.example.myapplication.ui

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.myapplication.R
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class Search : AppCompatActivity() {
    lateinit var cityedit:EditText;
    lateinit var btnser: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btnser=findViewById(R.id.sbtn)
        weatherTask()
        btnser.setOnClickListener{
            weatherTask().execute()
        }
    }
    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()

            findViewById<ProgressBar>(R.id.loaders).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainers).visibility = View.GONE
            findViewById<TextView>(R.id.errorTexts).visibility = View.GONE
        }
        override fun doInBackground(vararg params: String?): String? {
            cityedit=findViewById(R.id.searedit)
            val textt=cityedit.text
            var response:String?
            try{


                //  "https://api.openweathermap.org/data/2.5/weather?q=alexandria&units=metric&appid=$API"
                //  https://api.openweathermap.org/data/2.5/weather?q=$dhaka,bd&units=metric&appid=8789ec6f7fe211cc4333fb849ff4a8ff
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$textt&units=metric&appid=8789ec6f7fe211cc4333fb849ff4a8ff").readText(
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
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weatherDescription = jsonObj.getJSONArray("weather").getJSONObject(0)

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
                val  weatherdescription = weatherDescription.getString("description")
                val address = jsonObj.getString("name")+", "+sys.getString("country")
                /* Populating extracted data into our views */
                /* Populating extracted data into our views */

               // findViewById<TextView>(R.id.statuss).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temps).text = temp
                findViewById<TextView>(R.id.temp_mins).text = tempMin
                findViewById<TextView>(R.id.temp_maxs).text = tempMax

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loaders).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainers).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loaders).visibility = View.GONE
                findViewById<TextView>(R.id.errorTexts).visibility = View.VISIBLE
            }

        }
    }
}