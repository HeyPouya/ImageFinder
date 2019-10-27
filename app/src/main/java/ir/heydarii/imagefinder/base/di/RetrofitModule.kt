package ir.heydarii.imagefinder.base.di

import dagger.Module
import dagger.Provides
import ir.heydarii.imagefinder.retrofit.RetrofitMainInterface
import ir.heydarii.imagefinder.retrofit.RetrofitServiceGenerator
import ir.heydarii.imagefinder.utils.Consts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dagger module to provide Retrofit necessary objects
 */
@Module
class RetrofitModule {

    /**
     * Provides and configs okHttp
     */
    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        val httpClient = OkHttpClient().newBuilder()
        httpClient.connectTimeout(15, TimeUnit.SECONDS)
        httpClient.readTimeout(15, TimeUnit.SECONDS)
        httpClient.callTimeout(15, TimeUnit.SECONDS)
        httpClient.addInterceptor(interceptor)
        return httpClient
    }

    /**
     * Provides and configs logger to see the logs in terminal
     */
    @Singleton
    @Provides
    fun provedHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     * Provides GsonConvertor
     */
    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides the base url
     */
    @Singleton
    @Provides
    @Named("baseURL")
    fun provideBaseURL(): String {
        return Consts.BASE_URL
    }

    /**
     * Provides the retrofit interface
     */
    @Singleton
    @Provides
    fun provideMainInterface(retrofit: Retrofit): RetrofitMainInterface {
        return retrofit.create(RetrofitMainInterface::class.java)
    }

    /**
     * Provides the retrofit object
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        converterFactory: GsonConverterFactory,
        httpClient: OkHttpClient.Builder,
        @Named("baseURL")
        baseURL: String
    ): Retrofit {
        return RetrofitServiceGenerator(converterFactory, httpClient, baseURL).getClient()
    }


}