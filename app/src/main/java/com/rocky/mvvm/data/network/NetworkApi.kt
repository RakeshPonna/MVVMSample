package com.rocky.mvvm.data.network

import com.rocky.mvvm.data.network.responses.AuthResponse
import com.rocky.mvvm.data.network.responses.QuotesResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApi {

    /* @FormUrlEncoded
     @POST("login")
     fun userLogin(@Field("email") email: String, @Field("email") password: String): Call<ResponseBody>
 */
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>


    companion object {
        operator fun invoke(
            networkConnectionIntercepter: NetworkConnectionIntercepter
        ): NetworkApi {
            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(networkConnectionIntercepter).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("baseUrl")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkApi::class.java)
        }
    }

    /*companion object {
        operator fun invoke(): NetworkApi {
            val  okHttpClient = OkHttpClient.Builder().addInterceptor()


            return Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkApi::class.java)
        }
    }*/
}