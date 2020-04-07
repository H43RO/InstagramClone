package com.haerokim.instagramclone

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.haerokim.myapplication.RetrofitService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//프로젝트 전반에 걸쳐 retrofit 사용할 수 있게끔 함
class MasterApplication : Application() {

    lateinit var service: RetrofitService

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this) //Stetho 초기화
        createRetrofit()
        //chrome://inspect/#devices
    }

    fun createRetrofit() {
        //스마트폰에서 통신이 나갈 때 나가는 통신을 가로채서 original에 잡아두고 개조함
        val header = Interceptor {
            val original = it.request()


            if (checkIsLogin()) {  //로그인이 됐다면 개조 - header를 추가함
                //User Token 얻어옴 (Token이 null이 아닐 경우 let 이하를 실행하겠다
                getUserToken()?.let { token ->
                    val request = original.newBuilder()
                        .header("Authorization", "token "+token) //token 한칸 띄기 유의
                        .build()
                    //개조한 통신을 내보냄
                    it.proceed(request)
                }
            } else { //로그인이 안됐다면 오리지날 통신을 내보낸다 (개조없이)
                it.proceed(original)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header) //header 넣어줌
            .addNetworkInterceptor(StethoInterceptor()) //폰에서 들락날락하는 통신을 Stetho가 낚아챔
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) //위에서 만든 client 기입 (header도 포함돼있음)
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if (token != "null") return true
        else return false
    }

    fun getUserToken(): String {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if (token == "null") return "null"
        else return token
    }



}