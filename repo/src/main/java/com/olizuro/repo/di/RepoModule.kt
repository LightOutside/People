package com.olizuro.repo.di

import com.olizuro.repo.data.ILocalDataSource
import com.olizuro.repo.data.INetworkDataSource
import com.olizuro.repo.data.LocalDataSourceImpl
import com.olizuro.repo.data.NetworkDataSourceImpl
import com.olizuro.repo.domain.IRepository
import com.olizuro.repo.domain.RepoUseCasesImpl
import com.olizuro.repo.domain.RepositoryImpl
import dagger.Binds
import dagger.Module
import o.lizuro.core.repo.IRepoUseCases

@Module
interface RepoModule {
    @Binds fun bindsRepoUseCases(impl: RepoUseCasesImpl): IRepoUseCases
    @Binds fun bindsRepository(impl: RepositoryImpl): IRepository
    @Binds fun bindsNetworkDataSource(impl: NetworkDataSourceImpl): INetworkDataSource
    @Binds fun bindsLocalDataSource(impl: LocalDataSourceImpl): ILocalDataSource
}