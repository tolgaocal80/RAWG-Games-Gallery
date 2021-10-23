package com.tolgaocal.rawggamesgallery.database_files

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GAMES")
data class GameItem(
    @JvmField @PrimaryKey var id: String,
    @JvmField @ColumnInfo(name = "NAME") var name: String,
    @JvmField @ColumnInfo(name = "IMAGE") var image: String,
    @JvmField @ColumnInfo(name = "DESCRIPTION") var description: String,
    @JvmField @ColumnInfo(name = "RATING") var rating: String,
    @JvmField @ColumnInfo(name = "METACRITIC") var metacritic: String,
    @JvmField @ColumnInfo(name = "RELEASE_DATE") var releaseDate: String,
    @JvmField @ColumnInfo(name = "FAVORITE") var favorite: Boolean,
)