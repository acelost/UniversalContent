package com.acelost.universalcontent.views

import com.acelost.universalcontent.contentbased.container.ActivityContainer
import com.acelost.universalcontent.contentbased.core.Content
import com.acelost.universalcontent.contentbased.core.ContentContainer
import com.acelost.universalcontent.contentbased.impl.ProfileContent

class ProfileActivity : ActivityContainer(), ContentContainer.HasTitle {

    override fun createContent(): Content {
        return ProfileContent()
    }

    override fun setContentTitle(title: CharSequence?) {
        supportActionBar?.title = title
    }

}