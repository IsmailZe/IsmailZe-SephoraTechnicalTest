package sephora.android.testtechnique.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import sephora.android.testtechnique.data.repository.ItemRepository
import sephora.android.testtechnique.data.repository.ItemRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindItemsRepository(itemRepositoryImpl: ItemRepositoryImpl): ItemRepository

}
