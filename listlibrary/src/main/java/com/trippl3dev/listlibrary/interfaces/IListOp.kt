@file:Suppress("UNCHECKED_CAST")

package com.trippl3dev.listlibrary.interfaces

import java.util.ArrayList

interface IListOp<T>{

    /**
     * add Item to list
     * [T] type of itemData in the list
     * @param t is and [T] the item to be added
     */
    fun addItem(t:T)
    fun addItem(position: Int,t:T)

    fun getList(): ArrayList<T>?

    /**
     * remove item from the list
     * [T] type of itemData in the list
     * @param t is and [T] the item to be added
     */
    fun removeItem(t:T)

    /**
     * remove item at specific position
     * @param position is an [Int]  the define position of
     * an item to be removed
     */
    fun removeItemat(position:Int)

    /**
     * add some items as list into list of items in the ListView
     * [T] type of itemData in the list
     * @param list is an [List] of items that will be added
     */
    fun addList(list:List<T>)

    fun addList(position: Int,list:List<T>)

    /**
     * replace current list of items with new list
     * this method clear all current list items, then put the new list items data
     * [T] type of itemData in the list
     * @param list is an [List] of items to replace current list
     */
    fun setList(list:List<T>)

    /**
     * remove list of items from current list data
     * [T] type of itemData in the list
     * @param list is an [List] of data to be removed from current list
     */
    fun removeList(list:List<T>)


    /**
     * updating item data in the list
     * [T] type of itemData in the list
     * @param t is the new Item
     * @param position is the position of item in the list where update will happen
     */
    fun updateItem(t:T,position:Int)


    /**
     * method for converting data
     * as it used when there is input of data that vary of  [T]
     * as it convert that item to [T]
     * @param t is an any type of object that we will convert it
     * to the [T] the type of list item data
     */
    fun convert(t:Any):T{
       return t as T
    }

    /******
     * remove all items in the list
     */
    fun removeAll()
}