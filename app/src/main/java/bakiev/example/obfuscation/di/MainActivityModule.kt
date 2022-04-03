package bakiev.example.obfuscation.di

import bakiev.example.obfuscation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}