package com.bestweby.enewz.network;


import com.bestweby.enewz.model.Category.CategoryModel;
import com.bestweby.enewz.model.posts.post.PostModel;
import com.bestweby.enewz.model.posts.post.Reply;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Md Sahidul Islam on 23-Jan-19.
 */

public interface ApiInterface {


    @GET(ApiConfig.API_CATEGORIES)
    Call<List<CategoryModel>> getCategories(@QueryMap HashMap<String, String> hashMap);


    @GET(ApiConfig.API_POSTS)
    Call<List<PostModel>> getPosts(@QueryMap HashMap<String, String> hashMap);


    @GET(ApiConfig.API_POST_DETAIL)
    Call<PostModel> getPostDetails(@Path(ApiConfig.KEY_ID) Integer id, @QueryMap HashMap<String, String> hashMap);


    @GET(ApiConfig.API_COMMENT)
    Call<List<Reply>> getPostComments(@QueryMap HashMap<String, String> hashMap);

}
