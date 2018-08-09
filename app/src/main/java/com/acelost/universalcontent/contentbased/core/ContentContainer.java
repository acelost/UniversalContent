package com.acelost.universalcontent.contentbased.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ContentContainer {

    interface HasTitle {

        void setContentTitle(@Nullable CharSequence title);

    }

    interface Appearing {

        void requestAppearance(@NonNull Content requesting);

    }

    interface Disappearing {

        void requestDisappearance(@NonNull Content requesting);

    }

}
