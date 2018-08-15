package com.acelost.universalcontent.fragmentbased.properties;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public interface ResultHandler {

    void onResult(@NonNull String contentId, int resultCode, @NonNull Bundle data);

    class Helper {
        @Nullable
        public static ResultHandler getResultHandler(@NonNull Fragment fragment) {
            Object parent = fragment.getTargetFragment();
            if (parent instanceof ResultHandler) {
                return (ResultHandler) parent;
            }
            parent = fragment.getParentFragment();
            if (parent instanceof ResultHandler) {
                return (ResultHandler) parent;
            }
            parent = fragment.getActivity();
            if (parent instanceof ResultHandler) {
                return (ResultHandler) parent;
            }
            return null;
        }
    }

}
