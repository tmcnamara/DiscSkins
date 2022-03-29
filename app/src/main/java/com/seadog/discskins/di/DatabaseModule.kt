package com.seadog.discskins.di

import android.content.Context
import androidx.room.Room
import com.seadog.discskins.util.CSVProcessor
import com.seadog.discskins.repository.SkinsDatabase
import com.seadog.discskins.repository.SkinsRepository
import com.seadog.discskins.repository.dao.SkinsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideSkinsDao(skinsDatabase: SkinsDatabase): SkinsDao {
        return skinsDatabase.skinsDao()
    }

    @Provides
    @Singleton
    fun provideSkinsDatabase(@ApplicationContext appContext: Context): SkinsDatabase {
        return Room.databaseBuilder(
            appContext,
            SkinsDatabase::class.java,
            "RssReader"
        ).build()
    }

    @Provides
    fun provideSkinsRepository(skinsDao: SkinsDao): SkinsRepository {
        return SkinsRepository(skinsDao)
    }

    @Provides
    fun provideCSVProcessor(skinsRepository: SkinsRepository): CSVProcessor {
        return CSVProcessor(skinsRepository)
    }
}