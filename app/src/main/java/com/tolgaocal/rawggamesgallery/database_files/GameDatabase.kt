package com.tolgaocal.rawggamesgallery.database_files

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameItem::class], version = 1, exportSchema = false)

abstract class GameDatabase : RoomDatabase() {

    abstract fun dbDao(): DatabaseDao

    // game_files database as companion object (singleton pattern)
    companion object {
        private var gameDB: GameDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): GameDatabase {
            if (gameDB == null) {
                gameDB = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_files-db"
                ).build()
            }
            return gameDB!!
        }
    }




}