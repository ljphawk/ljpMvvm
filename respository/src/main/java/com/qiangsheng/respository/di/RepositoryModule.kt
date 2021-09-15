package com.qiangsheng.respository.di

import com.qiangsheng.respository.api.TestApiService
import com.qiangsheng.respository.repo.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


/*
 *@创建者       L_jp
 *@创建时间     6/4/21 1:35 PM.
 *@描述
 */
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideTestRepository(testApiService: TestApiService): TestRepository {
        return TestRepository(testApiService)
    }
}