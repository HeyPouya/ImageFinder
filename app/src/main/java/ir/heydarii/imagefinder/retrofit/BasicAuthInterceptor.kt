package ir.heydarii.imagefinder.retrofit

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Provides basic auth for retrofit based on user and pass provided by the website
 */
class BasicAuthInterceptor(user: String, pass: String) : Interceptor {

    private var credentials: String = Credentials.basic(user, pass)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}