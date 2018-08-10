package com.acelost.universalcontent.contentbased.content.impl

import com.acelost.universalcontent.contentbased.container.ActivityContainer
import com.acelost.universalcontent.contentbased.core.Content
import com.acelost.universalcontent.contentbased.core.ContentContainer

class ProfileActivity : ActivityContainer(), ContentContainer.HasTitle {

    override fun createContent(): Content {
        return ProfileContent()
    }

    override fun setContentTitle(title: CharSequence?) {
        supportActionBar?.title = title
    }

}