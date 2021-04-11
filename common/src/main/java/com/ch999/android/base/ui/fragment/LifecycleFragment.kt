package com.ch999.android.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger

open class LifecycleFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.t(tag()).d("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.t(tag()).d("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.t(tag()).d("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.t(tag()).d("onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.t(tag()).d("onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Logger.t(tag()).d("onStart")
    }

    override fun onResume() {
        super.onResume()
        Logger.t(tag()).d("onResume")
    }

    override fun onPause() {
        super.onPause()
        Logger.t(tag()).d("onPause")
    }

    override fun onStop() {
        super.onStop()
        Logger.t(tag()).d("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.t(tag()).d("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.t(tag()).d("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.t(tag()).d("onDetach")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Logger.t(tag()).d("onViewStateRestored$savedInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.t(tag()).d("onViewStateRestored$outState")
    }

    open fun tag(): String = "Lifecycle-${javaClass.simpleName}"
}