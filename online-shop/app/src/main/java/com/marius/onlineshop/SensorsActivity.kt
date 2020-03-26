package com.marius.onlineshop

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_sensors.*


class SensorsActivity : AppCompatActivity(), LocationListener, SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("asd", accuracy.toString())
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_GYROSCOPE -> {
                gyroscopeTv.text = "Gyroscope : "
                event.values.forEach {
                    gyroscopeTv.text = "${gyroscopeTv.text} ${it} "
                }
            }
            Sensor.TYPE_ACCELEROMETER->{
                accelerometerTv.text = "Accelerometer : "
                event.values.forEach {
                    gyroscopeTv.text = "${gyroscopeTv.text} ${it} "
                }
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        Log.d("asd", location.toString())
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

    private var lastLocation: Location? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showLocation()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request.
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = mutableListOf<Sensor>()
        sensors.add(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER))
        sensors.add(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE))
        sensors.add(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE))
        sensors.forEach{
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }


        //ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),1)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
        showLocation()
    }

    private fun showLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val res = checkCallingOrSelfPermission(permission)
        if (res == PackageManager.PERMISSION_GRANTED) {
            Log.d("asd", "ok")
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10000f, this)
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastLocation?.let { location ->
                locationTv.text = "${location.longitude} // ${location.latitude}"
            }
        }
    }
}
