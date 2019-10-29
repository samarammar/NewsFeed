package com.samar.newsfeeds.base.activity

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.Observable
import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.mosby3.ActivityMviDelegate
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.newsdata.module.LanguageModule
import com.newsdomain.state.BaseVS
import com.samar.newsfeeds.R
import com.samar.newsfeeds.application.MyApplication
import com.samar.newsfeeds.base.MVIBasePresenter
import com.samar.newsfeeds.base.activity.ActivityMVIDelegateImpl
import com.samar.newsfeeds.base.fragment.BaseFragment
import com.samar.newsfeeds.utils.networking.NetworkUtils




import io.reactivex.disposables.Disposable
import org.aviran.cookiebar2.CookieBar
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseMVIActivity<V, P> : MviActivity<V, P>(), BaseFragment.IActivity,
    NetworkUtils.ConnectionStatusCB where V : MvpView, P : MviPresenter<V, *> {
    override fun createPresenter(): P {
        return BasePresenter() as P
    }

    var delgate: ActivityMVIDelegateImpl<V, P>? = null
    var injectorAll: InjectorAll = InjectorAll()
    var isConnected: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        injectorAll.languageModule.isArabic.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                injectorAll.languageModule.persist(injectorAll.languageModule.getLocality()!!)
//                recreate()
            }
        })
        super.onCreate(savedInstanceState)

        setFont()

        setToolbar()

    }

    // set font by use calligraphy library
    private fun setFont() {
        var font: String? = null
        if (getLanguage().isLanguageArabic!!) {
            font = "fontr.ttf"
        } else {
            font = "fontr.ttf"

        }
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath(font)
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
    override fun onResume() {
        super.onResume()
        onConnectionChanged()
        isConnected = NetworkUtils.getNetworkUtils().isConnected()
        if (isConnected != null && isConnected!!) onConnected() else onDisconnected()
    }
    open fun setToolbar() {
//        findViewById<TextView>(R.id.title)?.text = getScreenTitle()
        setBackIcon(R.drawable.close_icon)
        findViewById<ImageView>(R.id.close)?.setOnClickListener{
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        findViewById<ImageView>(R.id.close)?.setOnClickListener{
            super.onBackPressed()
        }
    }


    private lateinit var toolbarTitle: TextView


    open fun udpdateToolBarText(@StringRes title: Int) {
        updateToolbarTitle(resources.getString(title))
    }

    open fun updateToolbarTitle(title: String) {
        if (this::toolbarTitle.isInitialized)
            toolbarTitle.text = title
    }

    fun View.isVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.isGone() {
        this.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    open fun getScreenTitle(): String = ""
    override fun getLanguage(): LanguageModule {
        return injectorAll.languageModule
    }

    //
//    override fun setScreenTitle(title: String?) {
////        findViewById<TextView>(R.id.title)?.text = title
//////        findViewById<TextView>(R.id.title)?.typeface  = Typeface.createFromAsset(assets,"fonts/fontB.ttf")
////        findViewById<TextView>(R.id.title_center)?.text = title
//////        findViewById<TextView>(R.id.title_center)?.typeface  = Typeface.createFromAsset(assets,"fonts/fontB.ttf")
//
//    }


    open fun setSideScreenTitle(title: String?) {
//        findViewById<TextView>(R.id.title_side)?.text = title
//        findViewById<TextView>(R.id.title)?.text = title
//        findViewById<TextView>(R.id.title_center)?.text = title
//
//        findViewById<TextView>(R.id.title).visibility = View.GONE
//        findViewById<TextView>(R.id.title_center).visibility = View.GONE
//        findViewById<TextView>(R.id.title_side).visibility = View.VISIBLE

    }


    override fun setScreenTitle(title: String?, type: Boolean) {
////        findViewById<TextView>(R.id.title)?.text = title
////        findViewById<TextView>(R.id.title_center)?.text = title
////        if (type){
////            findViewById<TextView>(R.id.title).visibility = View.GONE
////            findViewById<TextView>(R.id.title_center).visibility = View.VISIBLE
////        }else{
////            findViewById<TextView>(R.id.title).visibility = View.VISIBLE
////            findViewById<TextView>(R.id.title_center).visibility = View.GONE
////        }


//        findViewById<TextView>(R.id.title_center).text=title
    }

    override fun setBackIcon(imageResource: Int) {
//        findViewById<ImageView>(R.id.close)?.setImageResource(imageResource)
//        findViewById<ImageView>(R.id.close)?.visibility = View.VISIBLE
//        findViewById<ImageView>(R.id.close)?.alignusingRotation(!getLanguage().isLanguageArabic!!)
        findViewById<ImageView>(R.id.close)?.setOnClickListener{
            super.onBackPressed()
        }
    }

    //
    override fun showNetwork() {
//        Toast.makeText(this,resources.getString(R.string.internet_msg),Toast.LENGTH_LONG).show()
//        GlobalToast().showToast(this,resources.getString(R.string.internet_msg))
//        Log.e("akkk",";sfkle")

    }

    override fun hideAppBar(visibility: Boolean) {
//        if (visibility){
//            findViewById<View>(R.id.toolbar)?.visibility=View.VISIBLE
//            findViewById<TextView>(R.id.title).visibility = View.VISIBLE
//
//        }else{
//            findViewById<View>(R.id.toolbar)?.visibility=View.GONE
//            findViewById<TextView>(R.id.title).visibility = View.GONE
//
//        }
    }

    override fun setScreenTitle(title: String?) {

    }

    fun setBackIconRotate(imageResource: Int) {
//        findViewById<ImageView>(R.id.close)?.setImageResource(imageResource)
//        findViewById<ImageView>(R.id.close)?.visibility = View.VISIBLE
//        findViewById<ImageView>(R.id.close)?.alignusingRotation(getLanguage().isLanguageArabic!!)
        findViewById<ImageView>(R.id.close)?.setOnClickListener{
            super.onBackPressed()
        }
    }


    override fun attachBaseContext(newBase: Context?) {
        injectorAll.attach(newBase!!)
        super.attachBaseContext(CalligraphyContextWrapper.wrap(injectorAll.languageModule.attach(newBase)))

    }

    override fun getMvpDelegate(): ActivityMviDelegate<V, P> {
        if (delgate == null) {
            delgate = ActivityMVIDelegateImpl(this, this)
        }
        return delgate as ActivityMviDelegate<V, P>
    }

    fun application(): MyApplication {
        return this.applicationContext as MyApplication
    }

    fun setTextBold(textViewList: List<TextView>) {
        val Bold = Typeface.createFromAsset(this.assets, "font_bold.ttf")
        textViewList.forEach {
            it.typeface = Bold
        }
    }
    fun replaceFramgment(fragment: Fragment, id: Int) {
//        findViewById<View>(id)?.setState(StatesConstants.NORMAL_STATE)
        var theFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
        if (theFragment == null)
            supportFragmentManager.beginTransaction().replace(id, fragment, fragment::class.java.simpleName).commit()
    }

    fun addFramgment(fragment: Fragment, id: Int) {
//        findViewById<View>(id)?.setState(StatesConstants.NORMAL_STATE)
        var theFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
        if (theFragment == null)
            supportFragmentManager.beginTransaction().add(id, fragment, fragment::class.java.simpleName).commit()
    }

    fun replaceFramgment(fragment: Fragment, id: Int,tag:String) {
//        findViewById<View>(id)?.setState(StatesConstants.NORMAL_STATE)
        var theFragment = supportFragmentManager.findFragmentByTag(tag)
        if (theFragment == null)
            supportFragmentManager.beginTransaction().replace(id, fragment, tag).commit()
    }

    fun replaceFramgment(fragment: Fragment, id: Int, enter: Int, exit: Int) {
//        findViewById<View>(id)?.setState(StatesConstants.NORMAL_STATE)

        var theFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (theFragment == null)
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(enter, exit)
                .replace(id, fragment, fragment::class.java.name).commit()
    }

    fun replaceFramgmentRemoveIfFound(fragment: Fragment, id: Int,tag:String) {
//        findViewById<View>(id)?.setState(StatesConstants.NORMAL_STATE)
        var theFragment = supportFragmentManager.findFragmentByTag(tag)
        if (theFragment != null)
            supportFragmentManager.beginTransaction().remove(theFragment).commit()
        supportFragmentManager.beginTransaction().replace(id, fragment, tag).commit()
    }

    fun replaceFramgmentWithSave(fragment: Fragment, id: Int) {
//        findViewById<View>(id)?.setState(StatesConstants.NORMAL_STATE)

        var theFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (theFragment == null)
            supportFragmentManager.beginTransaction().replace(id, fragment, fragment::class.java.name)
                .addToBackStack(fragment::class.java.name)
                .commit()
    }

    fun popup(){
        supportFragmentManager.popBackStack()
    }
//    fun replaceFramgmentWithSave(fragment: Fragment, id: Int,enter:Int,exit:Int) {
//        findViewById<View>(id)?.setState(StatesConstants.NORMAL_STATE)
//
//        var theFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.name)
//        if (theFragment == null)
//            supportFragmentManager.beginTransaction()
//                    .setCustomAnimations(enter,exit)
//                    .replace(id, fragment, fragment::class.java.name)
//                    .addToBackStack(fragment::class.java.name)
//                    .commit()
//    }

    //Networking


    override fun onConnected() {
        isConnected = true
        CookieBar.dismiss(this)
    }

    override fun onDisconnected() {
        isConnected = false
//        CookieBar.build(this)
//                .setMessage(resources.getString(R.string.internet_msg))
//                .setDuration(100000)
//                .setBackgroundColor(R.color.red)
//                .setMessageColor(R.color.white)
//                .show()

    }


    private var disposable: Disposable? = null

    @SuppressLint("CheckResult")
    fun onConnectionChanged() {
        this.disposable = NetworkUtils.getNetworkUtils().getNetworkStatus().subscribe {
            when (it) {
                NetworkUtils.CONNECTED -> {
                    onConnected()
                }
                NetworkUtils.DISCONNECTED -> {
                    onDisconnected()
                }
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view = currentFocus
        val ret = super.dispatchTouchEvent(event)

        if (view is EditText) {
            val w = currentFocus
            val scrcoords = IntArray(2)
            if (w == null) return false
            w.getLocationOnScreen(scrcoords)
            val x = event.rawX + w.left - scrcoords[0]
            val y = event.rawY + w.top - scrcoords[1]
            if (event.action == MotionEvent.ACTION_UP && (x < w.left || x >= w.right
                        || y < w.top || y > w.bottom)
            ) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
            }
        }
        return ret
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    class BasePresenter : MVIBasePresenter<MvpView, BaseVS>() {
        override fun bindIntents() {
        }
    }

}