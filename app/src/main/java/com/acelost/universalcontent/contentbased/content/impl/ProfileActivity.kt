package com.acelost.universalcontent.contentbased.content.impl

import com.acelost.universalcontent.contentbased.container.ActivityContainer
import com.acelost.universalcontent.contentbased.core.Content
import com.acelost.universalcontent.contentbased.core.ContentContainer

class ProfileActivity : ActivityContainer() {

    override fun createContent(): Content {
        return ProfileContent()
    }

}