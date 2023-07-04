package com.bestweby.enewz.cache.constant;

/**
 * Created by Md Sahidul Islam on 20-Jan-19.
 */

public class AppConstants {

    /* Constant Value */
    public static final String EMPTY_STRING = "";
    public static final String PERCENTAGE_SYMBOL = "%";
    public static final String VARIATION_FEATURED = "featured";
    public static final String VARIATION_CATEGORY = "category";

    /* Network Codes */
    public static final int EMPTY_RESULT = 400;

    /* Request Codes */
    public static final int FB_REQUEST_CODE = 101;
    public static final int GP_REQUEST_CODE = 102;


    // Youtube Api Key
    public static final String YOUTUBE_API_KEY = "AIzaSyAu1oJCoKvtGwFgldG_RjjvvMEC-Zx2yS8";


    // date time format
    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_DMY_SHORT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_DMY_MEDIUM = "dd MMM, yyyy";
    public static final String DATE_FORMAT_YMD_SHORT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DMY_LONG = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMAT_YMD_LONG = "yyyy-MM-dd HH:mm:ss";

    // Search key
    public static final int PAGE_NUMBER = 1;
    public static final int PER_PAGE = 10;
    public static final int MAX_PER_PAGE = 20;
    public static final int CATEGORY_PER_PAGE = 99;
    public static final String SEARCH_KEY = "searchKey";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_ASC = "asc";
    public static final String KEY_DESC = "desc";

    // bundle key
    public static final String BUNDLE_PAGE_TITLE = "page_title";
    public static final String BUNDLE_CATEGORY_ID = "category_id";
    public static final String BUNDLE_POST_ID = "post_id";
    public static final String BUNDLE_LAYOUT_TYPE = "layout_type";
    public static final String BUNDLE_WEB_URL = "web_url";
    public static final String BUNDLE_FROM_NOTIFICATION = "from_notification";
    public static final String BUNDLE_NOTIFICATION_DETAIL = "notification_detail";

    // Notification type
    public static final String NOTIFICATION_MESSAGE = "notification_message";
    public static final String NOTIFICATION_POST = "notification_post";
    public static final String NOTIFICATION_URL = "notification_url";


    public enum ListLayoutType {
        GRID,
        LINEAR
    }

    public enum OrientationTypeShow {
        VERTICAL,
        HORIZONTAL
    }

    public static final int KEY_FILE_PICKER = 777;
    public static final String GOOGLE_DOCS_VIEWER = "https://docs.google.com/viewerng/viewer?url=";

    public static final int SLIDER_DURATION = 5 * 1000;  //Increase value 5 to upper to slow down slider & decrease to faster slider load

    public static final String CSS_PROPERTIES_WHITE_FONT = "<style>body {width:100%;margin:0;;color:#ffffff} img {max-width:100%;height:auto;} iframe{width:100%;height:auto;}</style>";
    public static final String CSS_PROPERTIES_BLACK_FONT = "<style>body {width:100%;margin:0;;color:#000000} img {max-width:100%;height:auto;} iframe{width:100%;height:auto;}</style>";
}
