package com.tolgaocal.rawggamesgallery.game_files

import androidx.appcompat.app.AppCompatActivity
import com.tolgaocal.rawggamesgallery.database_files.GameDatabase
import com.tolgaocal.rawggamesgallery.database_files.GameItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GameModel(
    private val activity: AppCompatActivity,
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) {
    suspend fun updateGameItem(gameItem: GameItem) = withContext(coroutineContext) {
        GameDatabase.getDatabase(activity).dbDao().updateGameItem(gameItem)
    }
}