package com.acelost.universalcontent.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acelost.universalcontent.R
import com.acelost.universalcontent.contentbased.core.Content
import com.acelost.universalcontent.contentbased.core.ContentContainer
import com.acelost.universalcontent.contentbased.impl.ProfileContent

class MyFragment : Fragment(), ContentContainer {

    private lateinit var mContent: Content

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context?.let { context ->
            mContent = ProfileContent()
            return mContent.createView(context, null)
        } ?: return inflater.inflate(R.layout.content_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContent.attachToContainer(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mContent.detachFromContainer()
        mContent.destroyView()
    }

}