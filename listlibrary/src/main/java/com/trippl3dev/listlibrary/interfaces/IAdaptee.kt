@file:Suppress("unused")

package com.trippl3dev.listlibrary.interfaces

import android.databinding.ViewDataBinding
import android.view.View
import com.trippl3dev.listlibrary.BaseAdapter

/**
 * interface which implementers  used for Adapting the adapter of RecyclerView
 * @param T is the Type of List Item
 */

interface IAdaptee<T> {

    /**
     * setting the view Id for list item according to it's Type
     * @param viewType  is an Integer value for view Resource ex ( R.layout.list_item_view)
     * @return [Int] is viewId of item in the list
     */
    fun getViewID(viewType: Int): Int
    fun getErrorViewId():Int
    fun getLoadingViewId():Int
    /**
     * method for getting current list of adapter
     *@return [List] of  item in the list.
     */
    fun getList(): List<T>

    /**
     * method for setting list for adaptee
     * @param  list is and List of Items to be set for adapter
     */
    fun setList(list: List<T>)

    /**
     * method for getting type of view in list
     * @param position is an Integer value for position of item in  the list
     * @return [Int] type of the view
     */
    fun getTheViewType(position: Int): Int {
        return 0
    }

    /**
     * method that enable you to make list circular as it repeats its items again and again and ......
     * for example if you have list of {1,2,3} it will be {1,2,3,1,2,3,1,2,3,..... and so on} never end
     * @return [Boolean]  `true` set it circular or `false` set it not circular
     */
    fun getIsCircular(): Boolean {
        return false
    }

    /**
     * method that enable you for interaction with the view for each item
     * @param root is the View of an item in list
     * @param position is an Integer for position of item in List
     * for example setting action of and element of view
     * that is not only the way but you can do it in ViewModel of the list Item
     */
    fun onBindingView(root: View?, position: Int)

    fun getErrorLoadingType(state: Int): Int?{
        return if(state==States.ERROR)1111 else 2222
    }

    fun onErrorViewClicked()
    fun onBinding(binding: ViewDataBinding?, position: Int)

    fun setAdapter(adapter:BaseAdapter<T>)
    fun getAdapter():BaseAdapter<T>
}

