package com.acelost.universalcontent.fragmentbased.properties;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public interface ActionHandler {

    void onResult(@NonNull String contentId, int resultCode, @NonNull Bundle data);

    class Helper {
        @Nullable
        public static ActionHandler getResultHandler(@NonNull Fragment fragment) {
            Object parent = fragment.getTargetFragment();
            if (parent instanceof ActionHandler) {
                return (ActionHandler) parent;
            }
            parent = fragment.getParentFragment();
            if (parent instanceof ActionHandler) {
                return (ActionHandler) parent;
            }
            parent = fragment.getActivity();
            if (parent instanceof ActionHandler) {
                return (ActionHandler) parent;
            }
            return null;
        }
    }

}
