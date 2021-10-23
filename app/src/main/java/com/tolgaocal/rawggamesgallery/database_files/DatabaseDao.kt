package com.tolgaocal.rawggamesgallery.database_files

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
abstract class DatabaseDao {

    @Insert
    abstract fun insert(gameItem: GameItem)
    @Update
    abstract fun update(gameItem: GameItem)
    @Query("SELECT * FROM GAMES WHERE ID = :id")
    abstract fun getGameItemById(id: String): GameItem?

    @Query("SELECT * FROM GAMES")
    abstract fun getGameItemList(): MutableList<GameItem>?

    @Query("SELECT * FROM GAMES WHERE FAVORITE = 1")
    abstract fun getFavoriteGameItemList(): MutableList<GameItem>?

    @Query("SELECT * FROM GAMES WHERE NAME LIKE '%' || :searchString || '%' ")
    abstract fun getGameItemListByNameSearch(searchString: String): MutableList<GameItem>?

    fun addGameItem(gameItem: GameItem) = insert(gameItem)

    fun updateGameItem(gameItem: GameItem) = update(gameItem)
}

