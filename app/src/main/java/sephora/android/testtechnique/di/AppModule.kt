package sephora.android.testtechnique.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sephora.android.testtechnique.BaseApplication
import sephora.android.testtechnique.data.AppDatabase
import sephora.android.testtechnique.data.dao.ItemsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication =
        app as BaseApplication

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideItemsDao(appDatabase: AppDatabase): ItemsDao = appDatabase.ItemsDao()

}

const val DB_NAME = "items_database"
