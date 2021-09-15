package com.qiangsheng.respository.di

import android.content.Context
import com.qiangsheng.respository.api.TestApiService
import com.qiangsheng.respository.network.RETROFIT
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton


/*
 *@创建者       L_jp
 *@创建时间     6/4/21 4:01 PM.
 *@描述
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun providesTestApiService(): TestApiService {
        return RETROFIT.create(TestApiService::class.java)
    }
}