package com.acelost.universalcontent.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.acelost.universalcontent.contentbased.core.Content
import com.acelost.universalcontent.contentbased.core.ContentContainer
import com.acelost.universalcontent.contentbased.impl.ProfileContent

class MyActivity : AppCompatActivity(), ContentContainer, ContentContainer.Disappearing, ContentContainer.HasTitle {

    private var mContent: Content? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = ProfileContent()
        val view = content.createView(this, null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        findViewById<ViewGroup>(android.R.id.content).addView(view)
        content.attachToContainer(this)
        mContent = content
    }

    override fun onDestroy() {
        mContent?.let { content ->
            content.detachFromContainer()
            content.destroyView()
        }
        super.onDestroy()
    }

    override fun requestDisappearance(requesting: Content) {
        if (!isFinishing) {
            finish()
        }
    }

    override fun setContentTitle(title: CharSequence?) {
        supportActionBar?.title = title
    }

}