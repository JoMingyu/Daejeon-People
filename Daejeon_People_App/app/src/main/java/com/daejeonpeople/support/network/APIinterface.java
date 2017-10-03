package com.daejeonpeople.support.network;

import com.daejeonpeople.valueobject.CulturalItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by geni on 2017. 8. 22..
 */

public interface APIinterface {
    @FormUrlEncoded
    @POST("/signup")
    Call<Void> doSignUp(@Field("id") String id,
                        @Field("password") String password,
                        @Field("email") String email,
                        @Field("name") String name,
                        @Field("registration_id") String resistrationId);

    @FormUrlEncoded
    @POST("/signup/phone/demand")
    Call<Void> doPhoneDemand(@Field("number") String number);

    @FormUrlEncoded
    @POST("/signup/email/demand")
    Call<Void> doSignUpDemandE(@Field("email") String email);

    @FormUrlEncoded
    @POST("/signup/id/check")
    Call<Void> doIDcheck(@Field("id") String id);

    @FormUrlEncoded
    @POST("/signin")
    Call<Void> doSignIn(@Field("id") String id,
                        @Field("password") String passwrod);

    @FormUrlEncoded
    @POST("/logout")
    Call<Void> doLogOut();

    @FormUrlEncoded
    @POST("/change/password")
    Call<Void> doChangePassword(@Field("id") String id,
                                @Field("current_password") String currentPassword,
                                @Field("new_password") String newPassword);

    @FormUrlEncoded
    @POST("/signup/phone/verify")
    Call<Void> doPhoneVerify(@Field("number") String number,
                              @Field("code") String code);

    @FormUrlEncoded
    @POST("/signup/email/verify")
    Call<Void> doSignUpVerifyE(@Field("email") String email,
                               @Field("code") String code);

    @FormUrlEncoded
    @POST("/find/id/demand")
    Call<Void> doFindIdDemand(@Field("email") String email,
                              @Field("name") String name);

    @FormUrlEncoded
    @POST("find/id/verify")
    Call<Void> doFindIdVerify(@Field("email") String email,
                              @Field("code") String code);

    @GET("attractions/list")
    Call<JsonObject> getAttractionsList(@Header("cookie") String UserSession,
                                        @Query("content_type_id") int content_type,
                                        @Query("sort_type") int sort_type,
                                        @Query("page") int page);

    @GET("attractions/list/total")
    Call<JsonObject> getAttractionsListTotal(@Header("cookie") String UserSession,
                                             @Query("sort_type") int sort_type,
                                             @Query("page") int page);

    @GET("attractions/list/category")
    Call<JsonArray> getFilteringPage( @Header("cookie") String UserSession,
                                       @Query("category") String category,
                                       @Query("sort_type") int sort_type,
                                       @Query("page") int page);

    @GET("/attractions/result_list")
    Call<JsonObject> getAttractionsDetail();


    @GET("/user")
    Call<JsonObject> getUserInfo();

    @GET("/mypage")
    Call<JsonObject> getMyPage(@Header("cookie") String UserSession);

    @GET("/main-page")
    Call<JsonObject> getMainInfo(@Header("cookie") String UserSession,
                                 @Query("popular_count") int popluar_count,
                                 @Query("monthly_count") int monthly_count);

    @GET("/wish")
    Call<JsonObject> getWish(@Header("cookie") String UserSession);

    @FormUrlEncoded
    @POST("/wish")
    Call<Void> addWish(@Field("content_id") int content_id);

    @GET("/attractions/result_list")
    Call<JsonObject> getDetail(@Query("content_id") int content_id);

    @GET("/friend")
    Call<JsonObject> getFriend(@Header("cookie") String UserSession);

}
