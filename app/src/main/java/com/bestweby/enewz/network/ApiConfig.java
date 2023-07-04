package com.bestweby.enewz.network;

/**
 * Created by Md Sahiul Islam on 23-Jan-19.
 */

public class ApiConfig {

    private static final String SITE_URL = "https://www.ekotarkantho.com/"; //Change to your website url
    private static final String API_URL = "wp-json/wp/v2/";    //Don't change api url
    public static final String BASE_URL = SITE_URL + API_URL;


    //KEY CONSTANT
    public static final String KEY_ID = "id";
    public static final String KEY_SLUG = "slug";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_PAGE = "page";
    public static final String KEY_POST = "post";
    public static final String KEY_CATEGORIES = "categories";
    public static final String KEY_PER_PAGE = "per_page";
    public static final String KEY_EMBED = "_embed";
    public static final String KEY_SEARCH = "search";
    public static final String KEY_HIDE_EMPTY = "hide_empty";
    public static final String KEY_STICKY = "sticky";
    public static final String KEY_EXCLUDE = "exclude";
    public static final String KEY_AUTHOR_NAME = "author_name";
    public static final String KEY_AUTHOR_EMAIL = "author_email";
    public static final String KEY_COMMENT_CONTENT = "content";


    // API END POINTS
    public static final String API_CATEGORIES = "categories";
    public static final String API_POSTS = "posts";
    public static final String API_POST_DETAIL = "posts/{" + KEY_ID + "}";;
    public static final String API_MENUS = "menus/";
    public static final String API_COMMENT = "comments";
    public static final String API_TAG = "tags";


    public static final String HEADER_TOTAL_ITEM = "x-wp-total";
    public static final String HEADER_TOTAL_PAGE = "x-wp-totalpages";

}
