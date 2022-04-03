package bakiev.example.obfuscation.di

import bakiev.example.obfuscation.ObfuscationApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(githubApp: ObfuscationApp)
}
