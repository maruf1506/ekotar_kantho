package com.bestweby.enewz.network;

import com.bestweby.enewz.cache.constant.AppConstants;

import java.util.HashMap;

/**
 * Created by Md Sahidul Islam on 23-Jan-19.
 */
public class ApiRequests {

    public static HashMap<String, String> buildPostDetail() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(ApiConfig.KEY_EMBED, "true");
        return hashMap;
    }

    public static HashMap<String, String> buildCategory(Boolean hideEmpty, int perPage) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (hideEmpty) hashMap.put(ApiConfig.KEY_HIDE_EMPTY, "true");
        hashMap.put(ApiConfig.KEY_EMBED, "true");
        hashMap.put(ApiConfig.KEY_PER_PAGE, String.valueOf(perPage));
        return hashMap;
    }

    public static HashMap<String, String> buildCategoryPosts(int categoryId, int pageNo) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(ApiConfig.KEY_CATEGORIES, String.valueOf(categoryId));
        hashMap.put(ApiConfig.KEY_PAGE, String.valueOf(pageNo));
        hashMap.put(ApiConfig.KEY_EMBED, "true");
        hashMap.put(ApiConfig.KEY_PER_PAGE, String.valueOf(AppConstants.PER_PAGE));
        return hashMap;
    }



    public static HashMap<String, String> buildAllPosts(int per_page) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(ApiConfig.KEY_EMBED, "true");
        hashMap.put(ApiConfig.KEY_PER_PAGE, String.valueOf(per_page));
        return hashMap;
    }

    public static HashMap<String, String> buildSearchPosts(int pageNo, String searchKey) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(ApiConfig.KEY_SEARCH, searchKey);
        hashMap.put(ApiConfig.KEY_EMBED, "true");
        hashMap.put(ApiConfig.KEY_PAGE, String.valueOf(pageNo));
        hashMap.put(ApiConfig.KEY_PER_PAGE, String.valueOf(AppConstants.PER_PAGE));
        return hashMap;
    }
}
