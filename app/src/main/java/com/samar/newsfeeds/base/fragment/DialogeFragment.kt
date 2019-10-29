package com.samar.newsfeeds.base.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.*
import android.widget.FrameLayout
import com.common.utils.DimensionUtils
import com.samar.newsfeeds.R


const val DialogFRAGMENTNAME = "DialogFRAGMENTNAME"
const val LISTBUNDLE = "LISTBUNDLE"
const val XVALUE = "XVALUE"
const val YVALUE = "YVALUE"
const val WidthVALUE = "WidthVALUE"
const val HEIGHVALUE = "HEIGHVALUE"
const val DIMVALUE = "DIMVALUE"
const val MODEL = "MODEL"
const val IMAGE = "UMAGE"
const val TEXT = "TEXT"
const val BUTTONTEXT = "BUTTONTEXT"
const val TYPE = "TYPE"

class DialogeFragment : DialogFragment() {

    companion object {
        // open fragment with class name
        fun open(fragmentManager: FragmentManager, className: String) {
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            fragment.arguments = bundle
            fragment.show(fragmentManager, className)
        }

        //get instance from DialogeFragment
        fun openDilogFragment(fragmentManager: FragmentManager, className: String): DialogeFragment {
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            fragment.arguments = bundle
            fragment.show(fragmentManager, className)
            return fragment
        }
        //get instance from DialogeFragment
        fun openDilogFragment(fragmentManager: FragmentManager, className: String,data: Bundle): DialogeFragment {
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            bundle.putFloat(DIMVALUE, -1f)
            bundle.putBundle(LISTBUNDLE, data)
            fragment.arguments = bundle
            fragment.show(fragmentManager, className)
            return fragment
        }

        //open dialoge with class name and parent fragment
        fun open(fragmentManager: FragmentManager, className: String, parentFragment: Fragment) {
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            fragment.arguments = bundle
            fragment.setTargetFragment(parentFragment, 0)
            fragment.show(fragmentManager, className)
        }

        // open dialog with class name and bundle
        fun open(fragmentManager: FragmentManager, className: String, data: Bundle) {
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            bundle.putBundle(LISTBUNDLE, data)
            fragment.arguments = bundle
            fragment.show(fragmentManager, className)
        }


        // get instance of  dialog with class name and view to take it's params
        fun open(fragmentManager: FragmentManager, className: String, view: View): DialogFragment {
            view.invalidate()
            val rectf = Rect()
            view.getGlobalVisibleRect(rectf)
//            val location = IntArray(2)
//            view.getLocationInWindow(location)
            val x = view.x.toInt()
            val y = view.y.toInt()
            val width = rectf.width()
            val height = rectf.height()
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            bundle.putInt(XVALUE, x)
            bundle.putInt(YVALUE, y)
            bundle.putInt(WidthVALUE, width)
            bundle.putInt(HEIGHVALUE, height)
            bundle.putFloat(DIMVALUE, 0.0f)
            fragment.arguments = bundle
            fragment.show(fragmentManager, className)
            return fragment
        }

        //get instance of dialog with class name and view
        fun getDialog(className: String, view: View): DialogeFragment {
            view.invalidate()
            val rectf = Rect()
            view.getGlobalVisibleRect(rectf)
//            val location = IntArray(2)
//            view.getLocationInWindow(location)
            val x = view.x.toInt()
            val y = view.y.toInt()
            val width = rectf.width()
            val height = rectf.height()
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            bundle.putInt(XVALUE, x)
            bundle.putInt(YVALUE, y)
            bundle.putInt(WidthVALUE, width)
            bundle.putInt(HEIGHVALUE, height)
            bundle.putFloat(DIMVALUE, 0.0f)
            fragment.arguments = bundle
            return fragment
        }

        // get instance of dialog with class name and view
        fun open(className: String, view: View): DialogeFragment {
            view.invalidate()
            val rectf = Rect()
            view.getGlobalVisibleRect(rectf)
            val location = IntArray(2)
            view.getLocationInWindow(location)
            val x = view.x
            val y = view.y
            val width = view.width
            val height = 0
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            bundle.putInt(XVALUE, x.toInt())
            bundle.putInt(YVALUE, y.toInt())
            bundle.putInt(WidthVALUE, width)
            bundle.putInt(HEIGHVALUE, height)
            bundle.putFloat(DIMVALUE, -1f)
//            bundle.putBundle(LISTBUNDLE,bundle)

            fragment.arguments = bundle
            return fragment
        }

        // get instance of dialoge with class name , view and bundle
        fun open(className: String, view: View, data: Bundle): DialogeFragment {
            view.invalidate()
            val rectf = Rect()
            view.getGlobalVisibleRect(rectf)
//            val location = IntArray(2)
//            view.getLocationInWindow(location)
            val x = view.x.toInt()
            val y = rectf.bottom
            val width = rectf.width()
//            val height = (DimensionUtils.getScreenHeight(view.context!!,true)*0.4f).toInt()
            val height = 0
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            bundle.putInt(XVALUE, x)
            bundle.putInt(YVALUE, y)
            bundle.putInt(WidthVALUE, width)
            bundle.putInt(HEIGHVALUE, height)
            bundle.putFloat(DIMVALUE, -1f)
            bundle.putBundle(LISTBUNDLE, data)

            fragment.arguments = bundle
            return fragment
        }

        // get instance of dialoge with class name , view and bundle
        fun openBelow(className: String, view: View, data: Bundle): DialogeFragment {
            view.invalidate()
            val rectf = Rect()
            view.getGlobalVisibleRect(rectf)
            val location = IntArray(2)
            view.getLocationInWindow(location)
            val x = location[0] + rectf.width()/2
            val y = location[1] + rectf.height()/4
//            val width = rectf.width()
//            val height = (DimensionUtils.getScreenHeight(view.context!!,true)*0.4f).toInt()
            val height = 0
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            bundle.putInt(XVALUE, x)
            bundle.putInt(YVALUE, y)
            bundle.putInt(WidthVALUE, -1)
            bundle.putInt(HEIGHVALUE, 0)
            bundle.putFloat(DIMVALUE, 0f)
            bundle.putBundle(LISTBUNDLE, data)

            fragment.arguments = bundle
            return fragment
        }
        fun openBelow(className: String, view: View): DialogeFragment {
            view.invalidate()
            val rectf = Rect()
            view.getGlobalVisibleRect(rectf)
            val location = IntArray(2)
            view.getLocationInWindow(location)
            val x = location[0] - rectf.width()/2
            val y = location[1] + rectf.height()/4
//            val width = rectf.width()
//            val height = (DimensionUtils.getScreenHeight(view.context!!,true)*0.4f).toInt()
            val height = 0
            val fragment = DialogeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            bundle.putInt(XVALUE, x)
            bundle.putInt(YVALUE, y)
            bundle.putInt(WidthVALUE, -1)
            bundle.putInt(HEIGHVALUE, 0)
            bundle.putFloat(DIMVALUE, 0f)

            fragment.arguments = bundle
            return fragment
        }

    }



    // variables
    var listListener: IListItemSelected<*>? = null
    var dialogInterfac: DialogInterface? = null


    // set dismiss listener
    fun setDismissListner(dialogInterfac: DialogInterface?) {
        this.dialogInterfac = dialogInterfac
    }

    fun <T> setIListItemSelected(listener: IListItemSelected<T>): DialogeFragment {
        listListener = object : IListItemSelected<T> {
            override fun onItemSelected(t: T) {
                dismiss()
                listener.onItemSelected(t)
            }
        }
        return this
    }

    // dismiss override methode
    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        dialogInterfac?.dismiss()

    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        dialogInterfac?.cancel()
    }


    fun showDialog(fragmentManager: FragmentManager) {
        show(fragmentManager, getFragmentClassName())
    }

    fun getFragmentClassName(): String? {
        return arguments?.getString(FRAGMENTNAME, null)
    }

    //lifeycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val container = FrameLayout(context)
        container.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        container.id = R.id.container
        return container
    }


    fun width(): Int? {
        return arguments?.getInt(WidthVALUE, 0)
    }

    fun height(): Int? {
        return arguments?.getInt(HEIGHVALUE, 0)
    }

    fun x(): Int? {
        return arguments?.getInt(XVALUE, 0)
    }

    fun y(): Int? {
        return arguments?.getInt(YVALUE, 0)
    }

    fun dim(): Float? {
        return arguments?.getFloat(DIMVALUE, 0.0f)
    }


    fun getFragment(): Class<Fragment>? {
        val clazz: Class<*>?
        try {
            clazz = Class.forName(getFragmentClassName())

            return clazz as Class<Fragment>
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun openFragment() {
        val fragmentClass = getFragment() ?: return
        val fragment = fragmentClass.newInstance() ?: return

        fragment.arguments = arguments?.getBundle(LISTBUNDLE)

        if (fragment is IDialog) {
            fragment.setDialogFragment(this)
        }
        if (fragment is IListListenerSetter) {
            fragment.setListener(listListener!!)
        }
        childFragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openFragment()
    }

    override fun onStart() {
        super.onStart()

//                dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)


    }

    override fun onResume() {
//        prepareAttributes()
        super.onResume()
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    fun prepareAttributes(){
        if (y() != 0) {
            val window = dialog.window
            val size = Point()
            // Store dimensions of the screen in `size`
            val display = window!!.windowManager.defaultDisplay
            display.getSize(size)
            // Set the width of the dialog proportional to 75% of the screen width
            window.setGravity(Gravity.TOP)
            dialog.window.setGravity(Gravity.TOP xor Gravity.LEFT)
        }
        val windowManagerParams = WindowManager.LayoutParams()
        windowManagerParams.copyFrom(dialog.window.attributes)
        if (width() != -1)
            windowManagerParams.width = if (width() == 0) DimensionUtils.getScreenWidthInPixels(context!!, true)
            else width()!!
        else
            windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT

        windowManagerParams.height = if (height() == 0) WindowManager.LayoutParams.WRAP_CONTENT
        else height()!!


        windowManagerParams.x = x()!! - windowManagerParams.width/2
        windowManagerParams.y = y()!!


        if (dim() != -1f)
            windowManagerParams.dimAmount = dim()!!// dim only a little bit
// set "origin" to top left corner
        dialog.window.attributes = windowManagerParams
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    fun prepareAttributes(width:Int){
        val windowManagerParams = dialog.window.attributes
        windowManagerParams.x = x()!! - width/2
        if (dim() != -1f)
            windowManagerParams.dimAmount = dim()!!// dim only a little bit
// set "origin" to top left corner
        dialog.window.attributes = windowManagerParams
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun prepareAttributes(width:Int,height:Int){
        if (y()!! +height < DimensionUtils.getScreenHeight(context!!,true)){
            prepareAttributes(width)
        }else{
            val windowManagerParams = dialog.window.attributes
            windowManagerParams.x = x()!! - width/2
            windowManagerParams.y = y()!! - height
            if (dim() != -1f)
                windowManagerParams.dimAmount = dim()!!// dim only a little bit
            dialog.window.attributes = windowManagerParams
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    }

//    private fun setDialogPosition(source:View) {
//        // Find out location of source component on screen
//        // see http://stackoverflow.com/a/6798093/56285
//        val location = IntArray(2)
//        source.getLocationOnScreen(location)
//        val sourceX = location[0]
//        val sourceY = location[1]
//
//        val window = dialog.window
//
//        // set "origin" to top left corner
//        window!!.setGravity(Gravity.TOP or Gravity.LEFT)
//
//        val params = window.attributes
//
//        // Just an example; edit to suit your needs.
//        params.x = sourceX - source.width // about half of confirm button size left of source view
//        params.y = sourceY - source.height // above source view
//
//        window.attributes = params
//    }
}

interface IListItemSelected<T> {
    fun onItemSelected(t: T)
}

interface IListListenerSetter {
    fun setListener(listener: IListItemSelected<*>)
}