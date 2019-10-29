package com.samar.newsfeeds.ui.mainscreen

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.newsdomain.model.Article
import com.samar.newsfeeds.R

import com.trippl3dev.listlibrary.implementation.SubBaseListVM
import com.trippl3dev.listlibrary.interfaces.IListCallback

class newsfeedsVM : SubBaseListVM<Any, INewsFeedsList>() {
    override fun getViewId(type: Int): Int {
        return R.layout.item_news
    }

    override fun onBindView(root: View?, position: Int) {
        super.onBindView(root, position)
        val item = getListOp().getList()?.get(position)
        root?.setOnClickListener {
            listCallback.onItemClick(position, item!! as Article)
        }


    }





    override fun getLayoutManager(context: Context): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 1) as RecyclerView.LayoutManager
    }


}

interface INewsFeedsList : IListCallback {
    fun onItemClick(positon: Int, item: Article)
}