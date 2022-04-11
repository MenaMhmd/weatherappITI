package com.example.myapplication.gps

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.R
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class weathergps : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weathergps)
        weatherTask().execute()
    }
    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loaderm).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainerm).visibility = View.GONE
            findViewById<TextView>(R.id.errorTextm).visibility = View.GONE
        }
        override fun doInBackground(vararg params: String?): String? {
            val intentt=getIntent();
            val lonngps = intentt.getDoubleExtra("lonnngps",0.00)
            val lattgps = intentt.getDoubleExtra("latttgps",0.00)
            var response:String?
            try{


                //  "https://api.openweathermap.org/data/2.5/weather?q=alexandria&units=metric&appid=$API"
                //  https://api.openweathermap.org/data/2.5/weather?q=$dhaka,bd&units=metric&appid=8789ec6f7fe211cc4333fb849ff4a8ff
                response = URL("https://api.openweathermap.org/data/2.5/weather?lat=$lattgps&lon=$lonngps&appid=8789ec6f7fe211cc4333fb849ff4a8ff").readText(
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
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")
                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.addressm).text = address
                findViewById<TextView>(R.id.updated_atm).text =  updatedAtText
                findViewById<TextView>(R.id.statusm).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.tempm).text = temp
                findViewById<TextView>(R.id.temp_minm).text = tempMin
                findViewById<TextView>(R.id.temp_maxm).text = tempMax


                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loaderm).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainerm).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loaderm).visibility = View.GONE
                findViewById<TextView>(R.id.errorTextm).visibility = View.VISIBLE
            }

        }
    }
}