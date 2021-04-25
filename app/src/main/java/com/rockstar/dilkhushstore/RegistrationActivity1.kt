package com.rockstar.dilkhushstore

import com.google.android.gms.location.FusedLocationProviderClient

class RegistrationActivity1:BaseActivity(){

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var TAG:String="DashBoardActivity"
    var city: String=""

    override fun getActivityLayout(): Int {
        return R.layout.activity_registration
    }
}