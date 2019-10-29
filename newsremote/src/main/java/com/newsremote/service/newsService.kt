package com.newsremote.service


import com.newsremote.model.ArticlesResponce
import com.newsremote.model.SuccessResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface newsService {
//    @FormUrlEncoded
//    @POST("{user}/login")
//    fun login(
//        @Path("user") user: String,
//
//        @Field("phone") phone: String,
//        @Field("password") password: String
//    ): Observable<Response<SuccessResponse>>
//
//
//
//    @FormUrlEncoded
//    @POST("{user}/update/player_id")
//    fun updatePlayerId(
//        @Path("user") user: String?,
//        @Field("player_id") playerId: String?
//    ): Observable<Response<SuccessResponse>>


    @GET("articles")
    fun getArticles(
            @Query("source") source: String,
            @Query("apiKey") apiKey: String
    ): Observable<Response<ArticlesResponce>>




}