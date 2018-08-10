package com.acelost.universalcontent.fragmentbased.container;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.acelost.universalcontent.fragmentbased.container.properties.HasTitle;

public abstract class ActivityContainer extends AppCompatActivity implements HasTitle {

    protected abstract Fragment createContentFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment content = createContentFragment();
        // todo как управлять layoutParams при вставке?
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, content)
                .commit();
    }

    @Override
    public void setContentTitle(@Nullable CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

}
