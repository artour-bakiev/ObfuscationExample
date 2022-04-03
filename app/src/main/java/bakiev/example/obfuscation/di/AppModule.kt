package bakiev.example.obfuscation.di

import bakiev.example.obfuscation.network.api.GithubService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): GithubService {
        return GithubService.provideGithubService()
//        return Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(GithubService::class.java)
    }
}
