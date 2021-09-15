package com.zhixing.zuche.di

import android.app.Application
import com.zhixing.zuche.application.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/*
 *@创建者       L_jp
 *@创建时间     6/5/21 3:01 PM.
 *@描述
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun providesMyApplication(application: Application): MyApplication {
        return application as MyApplication
    }

}