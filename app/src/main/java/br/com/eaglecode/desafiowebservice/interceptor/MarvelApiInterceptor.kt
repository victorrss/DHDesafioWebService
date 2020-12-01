package br.com.eaglecode.desafiowebservice.interceptor

import br.com.eaglecode.desafiowebservice.keys.Keys
import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

class MarvelApiInterceptor :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val ts: String = (System.currentTimeMillis() / 1000).toString();
        val s = ts + Keys.apiPrivateKey + Keys.apiPublicKey
        val m = MessageDigest.getInstance("MD5")
        m.update(s.toByteArray(), 0, s.length);

        var hash: String = BigInteger(1, m.digest()).toString(16)

        val request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", Keys.apiPublicKey)
            .addQueryParameter("hash", hash)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}