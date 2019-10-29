package com.newsremote

import android.content.Context
import org.json.JSONObject
import retrofit2.Response
import java.net.HttpURLConnection


//fun Observable<*>.retryWhenunAuthorized(refresh: Observable<*>) {
//    return this.retryWhen {
//        it.map { error ->
//            (error.message?.contains("token_expired", true)!! || error.message?.contains("token_invalid", true)!!)
//        }.flatMap (refresh)
//    }

fun Response<*>.getErrorMessage(context: Context): String {
    var message = "error"
    when (this.code()) {
//        422 -> {
////            message = context.getString(R.string.error_login)
//        }
        HttpURLConnection.HTTP_OK -> {
//            message = "out_login"
//         message =    context.getString(R.string.unAuthorized)
        }
//        HttpURLConnection.HTTP_UNAUTHORIZED -> {
//            message =    context.getString(R.string.unAuthorized)
//            val errorResponse = this.errorBody()?.string()
//            message = errorResponse?.getMessageFromJson()!!
//        }
//        HttpURLConnection.HTTP_INTERNAL_ERROR ->  {
//            val errorResponse = this.errorBody()?.string()
//            message = errorResponse?.getMessageFromJson()!!
//        }

        HttpURLConnection.HTTP_GATEWAY_TIMEOUT, HttpURLConnection.HTTP_CLIENT_TIMEOUT, HttpURLConnection.HTTP_BAD_GATEWAY -> {
            message = context.getString(R.string.timeOutError)
        }
        else -> {
            val errorResponse = this.errorBody()?.string()
            message = errorResponse?.getMessageFromJson()!!
        }
    }
    return message
}

fun String.getMessageFromJson(): String {
    try {
        val jsonObject = JSONObject(this)
        if (jsonObject.has("message"))
            return jsonObject.get("message") as String
        else if (jsonObject.has("error")) {
            val error = jsonObject.getJSONObject("error")
            if (error.has("message"))
                return error.get("message").toString()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}