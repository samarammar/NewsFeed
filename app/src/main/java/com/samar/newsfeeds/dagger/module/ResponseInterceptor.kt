package com.samar.newsfeeds.dagger.module

import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils
import com.newsdata.module.PreferenceModule

import okhttp3.Interceptor
import okhttp3.ResponseBody
import javax.inject.Inject

class ResponseInterceptor @Inject constructor(val preferenceModule: PreferenceModule) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val responseString = String(response.body()?.bytes()!!)
//        LogUtils.d("Response: $responseString")
        if (responseString.contains("token_expired", true)) {

            preferenceModule.removeToken()

            val action =
                "com.sreer.login"

            val intent = Intent(action)
            ActivityUtils.startActivity(intent)
            ActivityUtils.finishAllActivities()

//            refreshTokenUsecase.refresh().subscribe()
        } else if (responseString.contains("token_not_provided", true) || responseString.contains(
                "user_not_found",
                true
            ) || responseString.contains("token_invalid", true)
        ) {
//            logout.logout().subscribe {
            preferenceModule.removeToken()

            val action =
                "com.sreer.login"

            val intent = Intent(action)
            ActivityUtils.startActivity(intent)
            ActivityUtils.finishAllActivities()
        }
        return response.newBuilder()
            .body(ResponseBody.create(response.body()?.contentType(), responseString))
            .build()
    }
}