
package com.trippl3dev.listlibrary.interfaces

import android.support.v4.widget.NestedScrollView


interface IListFeature {

    fun setHaFixedSize(fixed: Boolean)
    fun setListVMCallback(listCallback:IListCallback)




    fun changeVM(className: String)
    fun resetData()
    fun setRecyclerListener(listener: android.support.v7.widget.RecyclerView.RecyclerListener)

    fun addOnChildAttachStateChangeListener(listener: android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener)

    fun clearOnChildAttachStateChangeListeners()

    fun addItemDecorator(decorator: android.support.v7.widget.RecyclerView.ItemDecoration)


    fun setLayoutManager(layout: android.support.v7.widget.RecyclerView.LayoutManager)

    fun getLayoutManager(): android.support.v7.widget.RecyclerView.LayoutManager?

    fun setOnFlingListener(onFlingListener: android.support.v7.widget.RecyclerView.OnFlingListener?)


    fun setRecycledViewPool(pool: android.support.v7.widget.RecyclerView.RecycledViewPool)

    fun addItemDecoration(decor: android.support.v7.widget.RecyclerView.ItemDecoration)

    fun addOnScrollListener(listener: android.support.v7.widget.RecyclerView.OnScrollListener)

    fun clearOnScrollListeners()

    fun scrollToPosition(position: Int)

    fun smoothScrollToPosition(position: Int)

    fun stopScroll()

    fun addOnItemTouchListener(listener: android.support.v7.widget.RecyclerView.OnItemTouchListener)

    fun removeOnItemTouchListener(listener: android.support.v7.widget.RecyclerView.OnItemTouchListener)

    fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean)

    fun setItemAnimator(animator: android.support.v7.widget.RecyclerView.ItemAnimator)

//    fun enableSnapeHelper()
    fun filter( value:Any):Boolean
    fun setState(state:Int)
    fun getState():Int
    fun setScrollView(scrollView: NestedScrollView)
    fun scrollTo(x: Int, y: Int)
    fun getAdaptee():IAdaptee<Any>?
}