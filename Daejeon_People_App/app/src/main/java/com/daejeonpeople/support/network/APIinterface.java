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
    Call<JsonArray> getWish(@Header("cookie") String UserSession);

    @FormUrlEncoded
    @POST("/wish")
    Call<Void> addWish( @Header("cookie") String UserSession,
                        @Query("content_id") int content_id);

    @GET("/attractions/detail")
    Call<JsonObject> getDetail( @Header("cookie") String UserSession,
                                @Query("content_id") int content_id);

    @GET("/friend")
    Call<JsonArray> getFriendList(@Header("cookie") String UserSession);

    @FormUrlEncoded
    @POST("/friend/request")
    Call<Void> friendRequest(@Header("cookie") String UserSession,
                             @Field("dst") String destination);

    @GET("/friend/request")
    Call<JsonArray> getRequestList(@Header("cookie") String UserSession);

    @FormUrlEncoded
    @POST("/friend/accept")
    Call<Void> acceptFriendRequest(@Header("cookie") String UserSession,
                                   @Field("requester_id") String requesterId);

    @FormUrlEncoded
    @POST("/friend/refuse")
    Call<Void> refuseFriendRequest(@Header("cookie") String UserSession,
                                   @Field("requester_id") String requesterId);

    @GET("/travel")
    Call<JsonArray> getTravelList(@Header("cookie") String UserSession);

    @GET("/attractions/list/keyworded")
    Call<JsonArray> getSearchResult(@Header("cookie") String UserSession,
                                    @Query("keyword") String keyword,
                                    @Query("sort_type") int sort_type,
                                    @Query("page") int page);

    @GET("/attractions/list/keyworded")
    Call<JsonArray> getSearchResult(@Header("cookie") String UserSession,
                                    @Query("keyword") String keyword,
                                    @Query("sort_type") int sort_type,
                                    @Query("page") int page,
                                    @Query("x") double x,
                                    @Query("y") double y);

    @FormUrlEncoded
    @POST("/user")
    Call<JsonObject> userInquery(@Field("id") String id);

    @FormUrlEncoded
    @POST("/travel")
    Call<JsonObject> makeTravel(@Header("cookie") String UserSession,
                                @Field("title") String title);

    @FormUrlEncoded
    @POST("/travel/invite")
    Call<Void> inviteFriend(@Header("cookie") String cookie,
                            @Field("dst") String destination,
                            @Field("topic") String topic,
                            @Field("msg") String message);

    @FormUrlEncoded
    @POST("/chat")
    Call<Void> sendMessage(@Header("cookie") String cookie,
                           @Field("topic") String topic,
                           @Field("type") String type,
                           @Field("content") String content);

    @FormUrlEncoded
    @POST("/chat/read")
    Call<JsonArray> inqueryMessages(@Header("cookie") String cookie,
                                    @Field("topic") String topic,
                                    @Field("idx") int index);
}
