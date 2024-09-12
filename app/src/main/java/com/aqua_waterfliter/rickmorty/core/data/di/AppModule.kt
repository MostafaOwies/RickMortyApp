package com.aqua_waterfliter.rickmorty.core.data.di

import com.aqua_waterfliter.rickmorty.core.data.IRepository
import com.aqua_waterfliter.rickmorty.core.data.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindHomeRepository(repository: Repository): IRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {
    @Provides
    @Singleton
    fun provideCoroutineScope() =
        CoroutineScope(Dispatchers.Default + SupervisorJob())
}
