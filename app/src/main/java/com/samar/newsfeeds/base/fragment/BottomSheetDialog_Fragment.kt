package com.samar.newsfeeds.base.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.samar.newsfeeds.R


const val FRAGMENTNAME = "FRAGMENTNAME"

class BottomSheetDialog_Fragment : BottomSheetDialogFragment() {

    companion object {
        fun open(fragmentManager: FragmentManager, className: String) {
            val fragment = BottomSheetDialog_Fragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENTNAME, className)
            fragment.arguments = bundle
            fragment.show(fragmentManager, className)
        }

        fun openDilogFragment(fragmentManager: FragmentManager, className: String, bundle_: Bundle): BottomSheetDialog_Fragment {
            val fragment = BottomSheetDialog_Fragment()
            val bundle = Bundle()
            bundle.putBundle("LISTBUNDLE", bundle_)
            bundle.putString(FRAGMENTNAME, className)
            fragment.arguments = bundle
            fragment.show(fragmentManager, className)
            return fragment
        }


    }
//    var listListener: IListItemSelected<*>? = null
    var dialogInterfac: DialogInterface? = null
    // set dismiss listener


//    fun <T> setIListItemSelected(listener: IListItemSelected<T>): BottomSheetDialog_Fragment {
//        listListener = object : IListItemSelected<T> {
//            override fun onItemSelected(t: T) {
//                dismiss()
//                listener.onItemSelected(t)
//            }
//        }
//        return this
//    }



    fun setDismissListner(dialogInterfac: DialogInterface?) {

        this.dialogInterfac = dialogInterfac

    }

    override fun onCancel(dialog: DialogInterface?) {

        super.onCancel(dialog)

        dialogInterfac?.cancel()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val container = FrameLayout(context)
        container.id = R.id.container
        return container
    }


    // dismiss override methode

    override fun onDismiss(dialog: DialogInterface?) {

        super.onDismiss(dialog)

        dialogInterfac?.dismiss()



    }

    fun getFragmentClassName(): String? {
        return arguments?.getString(FRAGMENTNAME, null)
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
        fragment.arguments = arguments
        fragment.arguments = arguments?.getBundle(LISTBUNDLE)

        if (fragment is IBottomSheet) {
            fragment.setBottomSheetDialog(this)
        }
//        if (fragment is IListListenerSetter) {
//            fragment.setListener(listListener!!)
//        }
        childFragmentManager.beginTransaction()
            .replace(R.id.container, fragment).commit()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openFragment()
    }

    override fun onResume() {

        super.onResume()

        dialog.window?.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundResource(android.R.color.transparent)


//
//        val bottomSheet = dialog.window?.findViewById(android.support.design.R.id.design_bottom_sheet) as FrameLayout




        //onInterceptTouchEvent



    }


}




