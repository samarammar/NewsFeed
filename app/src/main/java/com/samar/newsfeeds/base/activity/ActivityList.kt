package com.samar.newsfeeds.base.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.jakewharton.rxrelay2.PublishRelay
import com.newsdomain.state.BaseVS
import com.samar.newsfeeds.R
import com.samar.newsfeeds.application.MyApplication
import com.samar.newsfeeds.base.ActivityList_View
import com.samar.newsfeeds.dagger.component.ApplicationComponent


import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import com.trippl3dev.listlibrary.implementation.ArchitectureList
import com.trippl3dev.listlibrary.implementation.RecyclerListIm
import com.trippl3dev.listlibrary.interfaces.States
import io.reactivex.Observable


abstract class ActivityList<P, V> : BaseMVIActivity<V, P>(),
        ActivityList_View, ListCallback where P : MviPresenter<V, *>, V : ActivityList_View {
    private val intentRelay: PublishRelay<Int> = PublishRelay.create()
    var listHolder: RecyclerListIm? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (getContentViewId() >= 0)
        setTheContent()
        setToolbar()
//        addList()
    }

    fun getApplicationCompoent(): ApplicationComponent {
         return  (application as MyApplication).applicationComponent
    }

    open fun setTheContent() {
        setContentView(getContentViewId())
    }

    override fun getIntentList(): Observable<Int> {
        return intentRelay
    }


    override fun setToolbar() {
        super.setToolbar()
        findViewById<TextView>(R.id.title)?.text = getScreenTitle()
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

    fun getToolbarId(): Int {
        return 1
    }

    val retryIntent : PublishRelay<Boolean> = PublishRelay.create()
    override fun  retryIntent(): Observable<Boolean>{
        return retryIntent
    }
    abstract override fun getScreenTitle(): String


override fun render(baseVS: BaseVS){

        when(baseVS){
            is BaseVS.Loading ->{
                if(baseVS.type ==0)
                    if (listHolder?.operation?.getList()?.isEmpty()!!) {
                        findViewById<View>(getListContainerId())?.setState(StatesConstants.LOADING_STATE)
                    }else {
                        listHolder?.setState(States.LOADING)
                    }
            }
            is BaseVS.Error -> {
                    if (listHolder?.operation?.getList()?.isEmpty()!!) {
                        findViewById<View>(getListContainerId())?.setState(StatesConstants.ERROR_STATE)
                                ?.setOnClickListener {
                                    retryIntent.accept(true)
                                }
                    }else{
                        listHolder?.setState(States.ERROR)
                    }

            }
            is BaseVS.Empty -> {
                if(baseVS.type ==0)
                    if (listHolder?.operation?.getList()?.isEmpty()!!) {
//                        setEmptyView()?. setOnClickListener {
//                            retryIntent.accept(true)
//                        }
                    }
                    else
                        listHolder?.setState(States.NORMAL)
            }else -> {
            findViewById<View>(getListContainerId())?.setState(StatesConstants.NORMAL_STATE)
            listHolder?.setState(States.NORMAL)
            renderResult(baseVS)
        }
        }
}

//    fun setEmptyView():View{
//            val emptyView = findViewById<View>(getListContainerId())?.setState(StatesConstants.EMPTY_STATE)
//            val textView = emptyView?.findViewById<TextView>(R.id.emptyViewText)
//            textView?.text = getEmptyMessage()
//        return emptyView!!
//    }

    abstract fun getEmptyMessage(): String?


    fun addList() {
        ArchitectureList.get(supportFragmentManager)
                .init()
                .setVM(getVMClassName())
                .putListInContainerWithId(getListContainerId())
                .addListener(object : ArchitectureList.ListReadyCallbak {
                    override fun onListReady(baseListVM: RecyclerListIm?) {
                        listHolder = baseListVM
                        listHolder?.setListVMCallback(this@ActivityList)
                        onListReady()
                    }
                })
    }

    override fun accept(currentPage: Int) {
        intentRelay.accept(currentPage)
    }

    override fun onStop() {
        super.onStop()
    }


    abstract fun onListReady()
    abstract fun renderResult(baseVS: BaseVS)
    abstract fun getContentViewId(): Int
    abstract fun getVMClassName(): String
    abstract fun getListContainerId(): Int

    override fun onPause() {
//        findViewById<View>(getListContainerId()).setState(StatesConstants.NORMAL_STATE)
        super.onPause()
    }

}

