package com.samar.newsfeeds.application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.multidex.BuildConfig
import android.support.multidex.MultiDexApplication
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.ActivityUtils

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils

import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

import com.blankj.utilcode.util.LogUtils


import com.facebook.drawee.backends.pipeline.Fresco
import com.google.gson.Gson
import com.newsdata.module.PreferenceModule
import com.samar.newsfeeds.R
import com.samar.newsfeeds.dagger.component.ApplicationComponent
import com.samar.newsfeeds.dagger.component.DaggerApplicationComponent
import com.samar.newsfeeds.utils.networking.ConnectivityReciever

//import com.tripl3dev.prettystates.StatesConfigFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


const val BaseUrl = ""

open class MyApplication : MultiDexApplication(){





    lateinit var applicationComponent: ApplicationComponent

    private lateinit var networkDetector: ConnectivityReciever

    @Inject
    lateinit var preference: PreferenceModule

    override fun onCreate() {
        super.onCreate()
//        initLeakCanary()
        initLog()
        initCrash()
        //set font to all application
//        setFont()
        setUpDaggerComponent()
//        RxFileChooser.register(this)
//        Fresc
        Fresco.initialize(this)

        networkDetector = ConnectivityReciever()



        val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkDetector, intent)
//        StatesConfigFactory.intialize()
//            .initDefaultViews()
//        StatesConfigFactory.get()
//            .setDefaultEmptyLayout(R.layout.empty_state_layout)
//            .setDefaultErrorLayout(R.layout.error_state_layout)
//            .setDefaultLoadingLayout(R.layout.loading_state_layout)
//


////        StatesConfigFactory.intialize().initDefaultViews()
//        val handler = Handler()
//        handler.postDelayed(object :Runnable{
//            override fun run() {
//                if (preference.IsshowPopUp()!!) {
//                    val bundle = Bundle().apply {
//
//                        putSerializable(rate_dialog.Data, Gson().fromJson(preference.GetRatingData(),CDEUnratedModel::class.java) )
//                    }
//                    val fm = getActivity()?.supportFragmentManager
//                    val dialogFragment = rate_dialog()
//                    dialogFragment.arguments = bundle
//
//                    dialogFragment.show(fm, "Sample Fragment")
//                }            }
//        },1000)



    }

    // set font by use calligraphy library
    private fun setFont() {

        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()

                .setDefaultFontPath("fontr.ttf")

                .setFontAttrId(R.attr.fontPath)

                .build()

        )

    }

    fun callRefresh() {
//        if(preference.getToken() != null) {
//            val refresh = PeriodicWorkRequest.Builder(RefreshTokenWorker::class.java,30, TimeUnit.MINUTES)
//                    .build()
//            WorkManager.getInstance().cancelAllWork()
//            WorkManager.getInstance().enqueue(refresh)
//        }
    }

    open fun setUpDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder().application(this).build()
        applicationComponent.inject(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        networkDetector.abortBroadcast()
    }

    fun preparePicass() {


        val progressListener = object : ProgressListener {
            override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                val progress = (100 * bytesRead / contentLength).toInt()

                // Enable if you want to see the progress with logcat
//                 Log.v("Picasso Looooooading -- > ", "Progress: $progress%");
//                progressBar.setProgress(progress)
//                if (thanks) {
//                    Log.i(LOG_TAG, "Done loading")
//                }
            }
        }
        val mOkHttpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalResponse = chain.proceed(chain.request())
                return originalResponse.newBuilder()
                    .body(ProgressResponseBody(originalResponse.body(), progressListener))
                    .build()
            }
        }).build()

        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(mOkHttpClient))
        val built = builder.build()
//        built.setIndicatorsEnabled(true)
//        built.isLoggingEnabled = true

        Picasso.setSingletonInstance(built)

    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

//    fun initLeakCanary() {
//        // 内存泄露检查工具
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return
//        }
//        LeakCanary.install(this);
//    }

    // init it in ur application
    fun initLog() {
        val config = LogUtils.getConfig()
            .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
            .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
            .setGlobalTag(null)// 设置 log 全局标签，默认为空
            // 当全局标签不为空时，我们输出的 log 全部为该 tag，
            // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
            .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
            .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
            .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
            .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
            .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
            .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
            .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
            .setFileFilter(LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
            .setStackDeep(1)// log 栈深度，默认为 1
            .setStackOffset(0)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
            .setSaveDays(3)// 设置日志可保留天数，默认为 -1 表示无限时长
            // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
            .addFormatter(object : LogUtils.IFormatter<ArrayList<*>>() {
                override fun format(list: ArrayList<*>): String {
                    return "LogUtils Formatter ArrayList { " + list.toString() + " }";
                }
            });
        LogUtils.d(config.toString())
    }

    @SuppressLint("MissingPermission")
    fun initCrash() {
        CrashUtils.init { crashInfo, e ->
            LogUtils.e(crashInfo)
            AppUtils.relaunchApp()
        }
    }


}

internal interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}