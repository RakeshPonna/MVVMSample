package com.rocky.mvvm.data.repository

import com.rocky.mvvm.data.db.AppDatabase
import com.rocky.mvvm.data.db.entities.User
import com.rocky.mvvm.data.network.NetworkApi
import com.rocky.mvvm.data.network.SafeApiRequest
import com.rocky.mvvm.data.network.responses.AuthResponse


class UserRepository(
    private val api: NetworkApi,
    private val db: AppDatabase

) : SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest {
            api.userLogin(email, password)
        }
    }

    suspend fun userSignUP(name: String, email: String, password: String): AuthResponse {
        return apiRequest {
            api.userSignUp(name, email, password)
        }
    }

    suspend fun saveUser(user: User) = db
        .getUSerDao().upsert(user)

    fun getUser() = db.getUSerDao().getuser()
//    fun getQuotes() = db.getQuoteDao().getQuotes()
}

/*

class UserRepository : SafeApiRequest() {
    */
/*fun userLogin(email: String, password: String): LiveData<String> {
        val loginResponse = MutableLiveData<String>()

        // we should not depend on main API , this is bad practice
        NetworkApi().userLogin(email, password).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loginResponse.value = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful)
                    loginResponse.value = response.body()?.string()
                else
                    loginResponse.value = response.errorBody()?.string()
            }

        })

        return loginResponse
    }*//*


    // Using Coroutines
    */
/*suspend fun userLogin(email: String, password: String): Response<AuthResponse> {
        return NetworkApi().userLogin(email, password)
    }*//*


    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest {
            NetworkApi().userLogin(email, password)
        }
    }

}*/
