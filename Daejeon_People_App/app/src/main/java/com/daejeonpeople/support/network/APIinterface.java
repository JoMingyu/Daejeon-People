package com.daejeonpeople.support.network;

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

    @FormUrlEncoded
    @GET("attractions/list/total")
    Call<JsonObject> getAttractionsListTotal();

    @FormUrlEncoded
    @GET("attractions/list")
    Call<JsonObject> getAttractionsList();

    @GET("/user")
    Call<JsonObject> getUserInfo();

    @GET("/mypage")
    Call<JsonObject> getMyPage(@Header("cookie") String UserSession);
}
