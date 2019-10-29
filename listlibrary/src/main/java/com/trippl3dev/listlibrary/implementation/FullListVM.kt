package com.trippl3dev.listlibrary.implementation

import android.arch.lifecycle.*
import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.SnapHelper
import android.view.View
import com.trippl3dev.listlibrary.interfaces.IAdaptee
import com.trippl3dev.listlibrary.interfaces.IListCallback
import com.trippl3dev.listlibrary.interfaces.IListOp
import com.trippl3dev.listlibrary.interfaces.IListVM
import java.util.*


abstract class FullListVM<FROM, T, V : IListCallback> : IListVM<FROM, T, V>, ViewModel(), LifecycleObserver {

    private var adaptee: IAdaptee<T>
    private var operations: IListOp<T>
    var currentPage: Int = 0
    private var filterValue: Any? = null
    private var bundle: Bundle? = null
    var isLoading = false

    lateinit var listCallback: V
    var listSnap: SnapHelper? = null

    var layoutManager: android.support.v7.widget.RecyclerView.LayoutManager? = null


    private val currentList: MutableLiveData<ArrayList<T>> = MutableLiveData()

    init {
        currentList.value = ArrayList()
        currentPage = setPageStartIndex()

        adaptee = object : AdapteeIm<T>(currentList.value!!) {
            override fun onErrorViewClicked() {
                fetchData()
            }

            override fun getErrorViewId(): Int {
                return getErrorViewID()
            }

            override fun getLoadingViewId(): Int {
                return getLoadingViewID()
            }

            override fun onBindingView(root: View?, position: Int) {
                onBindView(root, position)
//                if(getSnapHelper().findSnapView(getListLayoutManager(root?.context!!)!!) != null){
//                    onViewSnapped(getListLayoutManager(root?.context!!)?.visib)
//                }
            }

            override fun getViewID(viewType: Int): Int {
                return getViewId(viewType)
            }

            override fun getTheViewType(position: Int): Int {
                return getViewType(position)
            }


            override fun getIsCircular(): Boolean {
                return isCircular()
            }

            override fun onBinding(binding: ViewDataBinding?, position: Int) {
                setBindiningModel(binding)

            }
        }
        operations = object : ListOperationIm<T>(currentList) {
            override fun map(it: Any): T {
                return mapFrom(it as FROM)
            }

        }


    }


    override fun getSnapHelper(): SnapHelper? {
        return null
    }

    override fun getLayoutManager(context: Context): android.support.v7.widget.RecyclerView.LayoutManager {
        return MyLinearLayoutManager(context, android.support.v7.widget.RecyclerView.HORIZONTAL, false)
    }


    final override fun getListLayoutManager(context: Context): android.support.v7.widget.RecyclerView.LayoutManager? {
        if (layoutManager == null) {
            layoutManager = getLayoutManager(context)
        }
        return layoutManager
    }

    override fun getLoadingViewID(): Int {
        return -1
    }

    override fun getErrorViewID(): Int {
        return -1
    }

    override fun isInNestedScroll(): Boolean = false

    override fun setPageStartIndex(): Int {
        return 0
    }

    final fun resetData() {
        currentPage = setPageStartIndex()
        operations.removeAll()
    }

    private var onStart: Boolean = true
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    final fun onStart() {
        if (onStart) {
            onStart = false
//            if (!hasLoadMore())
                fetchData()
        }
    }

    override fun setBindiningModel(binding: ViewDataBinding?) {

    }

    final override fun setIListCallback(callback: V) {
        super.setIListCallback(callback)
        this.listCallback = callback

    }

    final override fun getLiveDataList(): LiveData<ArrayList<T>> {
        return currentList
    }

    final fun setFilterValue(value: Any) {
        this.filterValue = value
    }

    final override fun setAdaptee(adapter: IAdaptee<T>) {
        this.adaptee = adapter


    }

    final override fun setOp(operation: IListOp<T>) {
        this.operations = operation
    }

    final override fun getListOp(): IListOp<T> {
        return this.operations
    }

    final override fun getAdaptee(): IAdaptee<T> {
        return this.adaptee
    }

    override fun fetchData() {
        super.fetchData()
        this.isLoading = true
        if (currentList.value?.isEmpty()!!) {
            currentPage = setPageStartIndex()-1
        }
        currentPage++

    }


    var onScrollListener: EndlessRecyclerViewScrollListener? = null

    final fun getScrollListener(layoutManager: android.support.v7.widget.RecyclerView.LayoutManager): EndlessRecyclerViewScrollListener {
        this.onScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager,setPageStartIndex()) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: android.support.v7.widget.RecyclerView) {
                if (!isLoading) {
                    fetchData()
                }
            }
        }
//        onScrollListener?.startingPageIndex = setPageStartIndex()
        return this.onScrollListener!!
    }

    final override fun filter(value: Any): List<T>? {
        return currentList.value?.filter { filterCondition(value, it) }
    }

    override fun onViewSnapped(view: View, position: Int) {

    }


}