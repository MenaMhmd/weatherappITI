package com.example.myapplication.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class Map : AppCompatActivity() , OnMapReadyCallback, GoogleMap.OnMapClickListener {
     val EXTRA_MESSAGE = "com.example.myapplication.message"
    var currentmarker: Marker?=null
    private lateinit var mMap: GoogleMap
   // private lateinit var binding: ActivityMapsBinding
    var currentlocation: Location?=null
    var fusedlocationproviderclient: FusedLocationProviderClient?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  binding = ActivityMapsBinding.inflate(layoutInflater)
       // setContentView(bindg.root)
        setContentView(R.layout.activity_map)
        fusedlocationproviderclient= LocationServices.getFusedLocationProviderClient(this)
        fetchlocation()

    }
    private fun fetchlocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
            return
        }

        val task = fusedlocationproviderclient?.lastLocation
        task?.addOnSuccessListener { location->
            if(location!=null)
            {
                this.currentlocation=location
                val mapFragment=supportFragmentManager.findFragmentById(R.id.map)as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
    }       // fusedlocationproviderclient?.lastLocation.getFusedLocationProviderClient(this)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            1000->if(grantResults.size>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                fetchlocation()
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener (this)
        /* val sydney = LatLng(-34.0, 151.0)
         mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
        val latlong= LatLng(currentlocation?.latitude!!,currentlocation?.longitude!!)
        drawermarker(latlong)
        mMap.setOnMarkerDragListener(object:GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if(currentmarker!=null)
                {
                    currentmarker?.remove()
                    val newlatling= LatLng(p0?.position.latitude,p0?.position.latitude)
                    drawermarker(newlatling)
                }
            }

            override fun onMarkerDragStart(p0: Marker) {

            }
        })
    }
    private fun drawermarker(latLng: LatLng)
    {
        var markerOptions= MarkerOptions().position(latLng).title("Iam Here")
            .snippet(getAddress(latLng.latitude,latLng.longitude)).draggable(true)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
        currentmarker= mMap.addMarker(markerOptions)
        currentmarker?.showInfoWindow()
    }
    private fun getAddress(lat:Double,lon:Double):String?
    {
        val geocoder= Geocoder(this, Locale.getDefault())
        val addresses=geocoder.getFromLocation(lat,lon,1)
        return addresses[0].getAddressLine(0).toString()
    }
    override fun onMapClick(p0: LatLng) {
        var markerOptions= MarkerOptions().position(p0).title(p0.toString())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        mMap.addMarker(markerOptions)
        val intent = Intent(this, locweather::class.java).apply {
            putExtra("lattt", p0.latitude)

            putExtra("lonnn", p0.longitude)
            System.out.println("********"+p0.toString());
        }
        startActivity(intent)
    }
}