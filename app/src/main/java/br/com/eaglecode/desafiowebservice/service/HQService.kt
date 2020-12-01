package br.com.eaglecode.desafiowebservice.service

import android.util.Log
import android.widget.Toast
import br.com.eaglecode.desafiowebservice.interceptor.MarvelApiInterceptor
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HQService {
    @GET("comics")
    suspend fun getHQs(@Query("offset") offset: Int): JsonObject

    @GET("comics/{id}")
    suspend fun getHQ(@Path("id") comicId: Int): JsonObject
}

class RetrofitBuilder() {
    companion object {
        fun getService(): HQService? {
            try {
                val client = OkHttpClient()
                    .newBuilder()
                    .addInterceptor(MarvelApiInterceptor())
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com/v1/public/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                return retrofit.create(HQService::class.java)
            } catch (e: Exception) {
                Log.i("RetrofitBuilder", e.message.toString())
                Toast.makeText(null, "Failed to connect", Toast.LENGTH_LONG).show()
                return null
            }
        }
    }
}

