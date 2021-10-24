package com.tolgaocal.rawggamesgallery.game_files

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolgaocal.rawggamesgallery.database_files.GameItem
import kotlinx.coroutines.launch

class GameViewModel(private val model: GameModel) : ViewModel() {

    fun updateFavorite(gameItem: GameItem) = viewModelScope.launch {
        model.updateGameItem(gameItem)
    }

}