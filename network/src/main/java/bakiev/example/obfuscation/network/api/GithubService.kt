package bakiev.example.obfuscation.network.api

import bakiev.example.obfuscation.network.pdo.RepoSearchResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    suspend fun searchRepos(@Query("q") query: String): RepoSearchResponse

    companion object {
        fun provideGithubService(okHttpClient: OkHttpClient): GithubService = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.newBuilder().build())
            .build()
            .create(GithubService::class.java)
    }
}
