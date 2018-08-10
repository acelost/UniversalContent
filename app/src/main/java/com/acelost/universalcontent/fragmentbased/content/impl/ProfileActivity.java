package com.acelost.universalcontent.fragmentbased.content.impl;

import android.support.v4.app.Fragment;

import com.acelost.universalcontent.fragmentbased.container.ActivityContainer;
import com.acelost.universalcontent.fragmentbased.content.ProfileContentFragment;

public class ProfileActivity extends ActivityContainer {

    @Override
    protected Fragment createContentFragment() {
        return new ProfileContentFragment();
    }

}
