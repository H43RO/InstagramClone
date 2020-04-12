package com.haerokim.myapplication
import com.haerokim.instagramclone.Post
import com.haerokim.instagramclone.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("json/students/")
    fun getStudentsList(): Call<ArrayList<PersonFromServer>>

    @POST("json/students/")
    fun createStudent(
        @Body params: PersonFromServer
    ): Call<PersonFromServer>

    @POST("json/students/")
    fun createStudentEasy(
        @Body person: PersonFromServer
    ): Call<PersonFromServer>

    @POST("user/signup/")
    @FormUrlEncoded //Field 사용시 추가해야하는 어노테이션
    fun register(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ): Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("/instagram/post/list/all/")
    fun getAllPosts(): Call<ArrayList<Post>>

    @Multipart
    @POST("instagram/post")
    fun uploadPost(
        @Part image: MultipartBody.Part,
        @Part("content") requestBody: RequestBody
    ) : Call<Post>

    @GET("instagram/post/list/")
    fun getUserPostList():Call<ArrayList<Post>>
}