package com.bestweby.enewz.cache.preference;

import android.content.Context;

/**
 * Created by Md Sahidul Islam on 24-Jan-19.
 */
public class ProfilePreference {

    public static void saveUserProfileData(Context context, String customerId, String firstName, String lastName, String userName, String email, String profileImage) {
        AppPreference.getInstance(context).setString(PrefKey.USER_ID, customerId);
        AppPreference.getInstance(context).setString(PrefKey.FIRST_NAME, firstName);
        AppPreference.getInstance(context).setString(PrefKey.LAST_NAME, lastName);
        AppPreference.getInstance(context).setString(PrefKey.USER_NAME, userName);
        AppPreference.getInstance(context).setString(PrefKey.EMAIL, email);
        AppPreference.getInstance(context).setString(PrefKey.PROFILE_IMAGE, profileImage);
    }

    public static String getCustomerId(Context context) {
        return AppPreference.getInstance(context).getString(PrefKey.USER_ID);
    }

    public static String getUserName(Context context) {
        return AppPreference.getInstance(context).getString(PrefKey.USER_NAME);
    }

    public static String getUserEmail(Context context) {
        return AppPreference.getInstance(context).getString(PrefKey.EMAIL);
    }

}
