package com.newsdata.module

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.newsdomain.diinterfaces.AppContext
import com.newsdomain.diinterfaces.ProjectScope

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import java.util.*
import javax.inject.Inject

@ProjectScope
class LocationModule @Inject constructor(@AppContext val appContext: Context){

    private var locationProvider: ReactiveLocationProvider = ReactiveLocationProvider(appContext)

    fun prepareLocationRequest(): LocationRequest? {
         val request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(2)
                .setInterval(1000)
        return request
    }

    fun prepareTrackingRequest(): LocationRequest? {
        val request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
        return request
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): Observable<Location>{
        if ( ContextCompat.checkSelfPermission( appContext, android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            return locationProvider.getUpdatedLocation(prepareLocationRequest())
        }
        throw IllegalAccessError()
    }

    fun trackMyLocation(): Observable<Location>{
        if ( ContextCompat.checkSelfPermission( appContext, android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            return locationProvider.getUpdatedLocation(prepareTrackingRequest())
        }
        throw IllegalAccessError()
    }

    fun getAddress(lat:Double,lng:Double):Observable<MutableList<Address>>{
       return Observable.create<MutableList<Address>> {
            val geocoder = Geocoder(appContext, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            it.onNext(addresses)
        }.subscribeOn(Schedulers.io())
    }


//    val address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//    val city = addresses[0].locality
//    val state = addresses[0].adminArea
//    val country = addresses[0].countryName
//    val postalCode = addresses[0].postalCode
//    val knownName = addresses[0].featureName // Only if available else return NULL

}