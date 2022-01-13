package com.example.usemap

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.usemap.databinding.ActivityMapsBinding
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val MY_PERMISSION_REQUEST_FINE_LOCATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        checkPermission()

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED){
            myLocationEnable()
        }else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        //一度許可を求めたことがあって拒否されている場合
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.
            permission.ACCESS_FINE_LOCATION), MY_PERMISSION_REQUEST_FINE_LOCATION)
        }else{
            //まだ許可を求めていない場合
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.
            permission.ACCESS_FINE_LOCATION), MY_PERMISSION_REQUEST_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            MY_PERMISSION_REQUEST_FINE_LOCATION ->{
                if (permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    myLocationEnable()
                }else{
                    showToast("現在位置は表示できません")
                }
            }
        }
    }

    private fun myLocationEnable() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.
            ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true
        }
    }

    private fun showToast(msg:String){
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.show()
    }
}