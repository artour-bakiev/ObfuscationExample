package bakiev.example.obfuscation.di

import bakiev.example.obfuscation.network.api.GithubService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): GithubService {
        return GithubService.provideGithubService(OkHttpClient())
    }
}
