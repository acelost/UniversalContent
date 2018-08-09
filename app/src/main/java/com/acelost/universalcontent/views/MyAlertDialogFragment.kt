package com.acelost.universalcontent.views

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acelost.universalcontent.contentbased.core.Content
import com.acelost.universalcontent.contentbased.core.ContentContainer
import com.acelost.universalcontent.contentbased.impl.ProfileContent

class MyAlertDialogFragment : DialogFragment(), ContentContainer {

    private lateinit var mContent: Content

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        context?.let { context ->
            mContent = ProfileContent()
            val view = mContent.createView(context, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            return builder.create()
        } ?: return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mContent.attachToContainer(this)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mContent.detachFromContainer()
        mContent.destroyView()
    }

}