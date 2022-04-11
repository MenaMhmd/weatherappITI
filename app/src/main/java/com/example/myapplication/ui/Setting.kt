package com.example.myapplication.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.R
import com.example.myapplication.gps.gps
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class Setting : AppCompatActivity(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

   lateinit var radiogrouplang:RadioGroup
    lateinit var radiogrouploc:RadioGroup
    lateinit var radiogroupnot:RadioGroup
    lateinit var btndatepicker: Button
    lateinit var btndatepicker2: Button
    lateinit var btntimepicker: Button
    lateinit var save: Button

    lateinit var language: Button
    lateinit var location: Button
    lateinit var notifcation: Button

    var day=0
    var month=0
    var year=0
    var hour=0
    var minute=0
//var temp:String
    var savedday=0
    var savedmonth=0
    var savedyear=0
    var savedhour=0
    var savedminute=0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        language=findViewById(R.id.languagebtn)
        location=findViewById(R.id.locationbtn)
        notifcation=findViewById(R.id.notificationbtn)
        radiogrouplang = findViewById(R.id.langgroup)
        radiogrouploc = findViewById(R.id.locatgroup)
        radiogroupnot = findViewById(R.id.not)

        location.setOnClickListener {
        val checkedRadioButtonId: Int = radiogrouploc.getCheckedRadioButtonId()
        if (checkedRadioButtonId == -1) {
            Toast.makeText(this,"not found",Toast.LENGTH_LONG).show()
        } else {
            if (checkedRadioButtonId == R.id.map) {
                val intent = Intent(this, Map::class.java)
               startActivity(intent)
            }else{
                if (checkedRadioButtonId == R.id.gpsradio) {
                    val intent = Intent(this, gps::class.java)
                    startActivity(intent)
                }
        }}}
        notifcation.setOnClickListener {
            val checkedRadioButtonId: Int = radiogroupnot.getCheckedRadioButtonId()
            if (checkedRadioButtonId == -1) {
                Toast.makeText(this,"not found",Toast.LENGTH_LONG).show()
            } else {
                if (checkedRadioButtonId == R.id.enable) {
                   // notificationfrom()
                    //******************notifiication
                    // Create an explicit intent for an Activity in your app
                    notificationfrom()


                }
            if (checkedRadioButtonId == R.id.unenable) {
                Toast.makeText(this,"unenable",Toast.LENGTH_LONG).show()
            }
        }
        }
        radiogrouplang.setOnClickListener {
        }
    }

    private fun getdatetimecalender()
    {
        val cal:Calendar = Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun notificationfrom()
    {
        val view= View.inflate(this, R.layout.customalertdialog,null)
        val builder=AlertDialog.Builder(this)
        builder.setView(view)

        val dialog= builder.create()
        dialog.show()
        //dialog.window?.setBackgroundDrawableResource(R.color.tr)
        dialog.setCancelable(false)
        //dialog.setContentView(R.layout.customalertdialog)
        btndatepicker=view.findViewById(R.id.from)
        btndatepicker2=view.findViewById(R.id.to)
        btntimepicker=view.findViewById(R.id.time)
        save=view.findViewById(R.id.saveb)
        save.setOnClickListener {

            val intent = Intent(this, Setting::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                .setContentTitle("weather today")
                .setContentText("temperature:")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(0, builder.build())
                createNotificationChannel()
            }
            dialog.dismiss()
        }
        val mycalendar=Calendar.getInstance()
        val datepicker= DatePickerDialog.OnDateSetListener {view,year,month,day->

            btntimepicker.setOnClickListener{
                val cal=Calendar.getInstance()
                //val starthour=cal.get(Calendar.HOUR_OF_DAY)
                //val startminute=cal.get(Calendar.MINUTE)
               // TimePickerDialog(this,starthour,startminute,false).show
                val timesetlistener=TimePickerDialog.OnTimeSetListener{timePicker,hour,minute->
                    cal.set(Calendar.HOUR_OF_DAY,hour)
                    cal.set(Calendar.MINUTE, minute)
                 //   timetv.text=SimpleDateFormat("HH:MM").format(cal.time)

                }
                TimePickerDialog(this,timesetlistener,cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),true).show()
            }

        }
        btndatepicker.setOnClickListener {
            DatePickerDialog(this,datepicker,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),
            mycalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        btndatepicker2.setOnClickListener {
            DatePickerDialog(this,datepicker,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),
                mycalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updatelabel(mycalendar:Calendar) {
        val myformat="dd-MM-yyyy"
        val sdf=SimpleDateFormat(myformat,Locale.UK)



    }
    private fun not()
    {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, Setting::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())
        }
    }
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}


    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
       savedday=p3
       savedmonth=p2
        savedyear=p1
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedhour=p1
        savedminute=p2
    }
    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }
        override fun doInBackground(vararg params: String?): String? {

            var response:String?
            try{


                //  "https://api.openweathermap.org/data/2.5/weather?q=alexandria&units=metric&appid=$API"
                //  https://api.openweathermap.org/data/2.5/weather?q=$dhaka,bd&units=metric&appid=8789ec6f7fe211cc4333fb849ff4a8ff
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=alexandria&units=metric&appid=8789ec6f7fe211cc4333fb849ff4a8ff").readText(
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
                val weatherDescription = jsonObj.getJSONArray("weather").getJSONObject(0)




                val temp = main.getString("temp")+"°C"

                val  weatherdescription = weatherDescription.getString("description")



            } catch (e: Exception) {
              e.printStackTrace()
            }

        }
    }
}