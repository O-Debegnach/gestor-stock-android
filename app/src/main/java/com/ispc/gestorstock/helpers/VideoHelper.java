package com.ispc.gestorstock.helpers;

import android.net.Uri;

public class VideoHelper {
    public static Uri getMedia(String mediaName, String packageName) {
        return Uri.parse("android.resource://" + packageName +
                "/raw/" + mediaName);
    }
}
