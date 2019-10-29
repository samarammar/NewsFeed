package com.samar.newsfeeds.base.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.TextView
import com.hannesdorfmann.mosby3.FragmentMviDelegate
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.jakewharton.rxrelay2.PublishRelay
import com.newsdata.module.LanguageModule
import com.samar.newsfeeds.application.MyApplication
import com.samar.newsfeeds.base.fragment.MVIDelegate
import com.samar.newsfeeds.utils.networking.NetworkUtils

import io.reactivex.disposables.Disposable

//ListCallback
abstract class BaseFragment<V, P> : MviFragment<V, P>(),
    NetworkUtils.ConnectionStatusCB where V : MvpView, P : MviPresenter<V, *> {
    //    @Inject
//    lateinit var preferenceModule: PreferenceModule
    var mviDelegate: MVIDelegate<V, P>? = null
    var isConnected: Boolean? = null
    private lateinit var iActivity: IActivity


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.iActivity = context as IActivity

        //        if (preferenceModule.IsshowPopUp()!!) {
//            val bundle = Bundle().apply {
//                putString(roomsPicker.adults, "")
//                putString(roomsPicker.child, "")
//                putString(roomsPicker.room, "")
//            }
//
//            val fm = context?.getActivity()?.supportFragmentManager
//            val dialogFragment = rate_dialog()
//            dialogFragment.arguments = bundle
//            dialogFragment.show(fm, "Sample Fragment")
//        }
    }

    fun application(): MyApplication {
        return context?.applicationContext as MyApplication
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
//        onConnectionChanged()

    }


    override fun onResume() {
        super.onResume()


        onConnectionChanged()
        isConnected = NetworkUtils.getNetworkUtils().isConnected()
        if (isConnected != null && isConnected!!) onConnected() else onDisconnected()
    }


    override fun getMvpDelegate(): FragmentMviDelegate<V, P> {
        if (mviDelegate == null)
            mviDelegate = MVIDelegate(this, this)
        return mviDelegate!!
    }

    fun setScreenTitle(title: String) {
        iActivity.setScreenTitle(title)
    }

    fun iActivity(): IActivity? = iActivity

    fun getLangaugeModel(): LanguageModule = iActivity.getLanguage()

    fun hideAppBar(visibility: Boolean) {
        iActivity.hideAppBar(visibility)
    }

    interface IActivity {
        fun setScreenTitle(title: String?)
        fun setScreenTitle(title: String?, type: Boolean)
        fun getLanguage(): LanguageModule
        fun setBackIcon(imageResource: Int)
        fun hideAppBar(visibility: Boolean)
        fun showNetwork()
    }

    fun setTextBold(textViewList: List<TextView>) {
        val Bold = Typeface.createFromAsset(activity!!.assets, "font_bold.ttf")
        textViewList.forEach {
            it.typeface = Bold
        }
    }

    fun replaceFramgment(fragment: Fragment, id: Int) {
        var theFragment = childFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (theFragment == null)
            childFragmentManager.beginTransaction().replace(id, fragment, fragment::class.java.name).commit()
    }

    fun addFramgment(fragment: Fragment, id: Int) {
        var theFragment = childFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (theFragment == null)
            childFragmentManager.beginTransaction().add(id, fragment, fragment::class.java.name).commit()
    }

    fun replaceFramgment(fragment: Fragment, id: Int, enter: Int, exit: Int) {
        var theFragment = childFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (theFragment == null)
            childFragmentManager.beginTransaction()
                .setCustomAnimations(enter, exit)
                .replace(id, fragment, fragment::class.java.name).commit()
    }

    fun replaceFramgmentRemoveIfFound(fragment: Fragment, id: Int) {
        var theFragment = childFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (theFragment != null)
            childFragmentManager.beginTransaction().remove(theFragment).commit()
        childFragmentManager.beginTransaction().replace(id, fragment, fragment::class.java.name).commit()
    }

    fun replaceFramgmentWithSave(fragment: Fragment, id: Int) {
        var theFragment = childFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (theFragment == null)
            childFragmentManager.beginTransaction().replace(id, fragment, fragment::class.java.name)
                .addToBackStack(fragment::class.java.name)
                .commit()
    }

    fun replaceFramgmentWithSave(fragment: Fragment, id: Int, enter: Int, exit: Int) {
        var theFragment = childFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (theFragment == null)
            childFragmentManager.beginTransaction()
                .setCustomAnimations(enter, exit)
                .replace(id, fragment, fragment::class.java.name)
                .addToBackStack(fragment::class.java.name)
                .commit()
    }

    open fun prepareSwipToRefresh(id: Int, publisher: PublishRelay<Boolean>) {
        val swipToRefreshLayout = view?.findViewById<SwipeRefreshLayout>(id)
        swipToRefreshLayout?.setOnRefreshListener {
            swipToRefreshLayout.isRefreshing = false
            publisher.accept(true)
        }

    }
    //Networking

    override fun onConnected() {
        isConnected = true
    }

    override fun onDisconnected() {
//        iActivity.showNetwork()
//                GlobalToast().showToast(context?.getActivity()?.parent!!,resources.getString(R.string.internet_msg))

        isConnected = false
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

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}

