package com.marius.onlineshop

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_sensors.*


class SensorsActivity : AppCompatActivity(), LocationListener {
    override fun onLocationChanged(location: Location?) {
        Log.d("asd",location.toString())
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var lastLocation: Location?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorList.adapter = ArrayAdapter<Sensor>(this, R.layout.sensor_layout, sensors)

        //ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)


        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val permission1 = Manifest.permission.ACCESS_COARSE_LOCATION
        val permission2 = Manifest.permission.ACCESS_FINE_LOCATION
        val res1 = checkCallingOrSelfPermission(permission1)
        val res2 = checkCallingOrSelfPermission(permission2)
        if (res1 == PackageManager.PERMISSION_GRANTED && res2 == PackageManager.PERMISSION_GRANTED) {
            Log.d("asd","ok")
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10000f, this)
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastLocation?.let { location ->
                locationTv.text ="${location.longitude} - ${location.latitude}"
            }
        }
    }
}
