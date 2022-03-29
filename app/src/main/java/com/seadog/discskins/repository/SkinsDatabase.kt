package com.seadog.discskins.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seadog.discskins.domain.util.ListDoubleConverter
import com.seadog.discskins.domain.util.ListIntConverter
import com.seadog.discskins.repository.dao.SkinsDao
import com.seadog.discskins.domain.model.Round
import com.seadog.discskins.domain.model.Scorecard
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Round::class, Scorecard::class],
    version = 1
)
@TypeConverters(ListIntConverter::class, ListDoubleConverter::class)
abstract class SkinsDatabase: RoomDatabase() {
    abstract fun skinsDao(): SkinsDao

    companion object {
        @Volatile
        private var INSTANCE: SkinsDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): SkinsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SkinsDatabase::class.java,
                    "skins_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}