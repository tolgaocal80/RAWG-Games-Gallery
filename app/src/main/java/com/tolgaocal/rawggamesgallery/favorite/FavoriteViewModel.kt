package com.tolgaocal.rawggamesgallery.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolgaocal.rawggamesgallery.database_files.GameItem
import kotlinx.coroutines.launch

class FavoriteViewModel(private val model: FavoriteModel) : ViewModel() {

    private val mutableGameItemList = MutableLiveData<List<GameItem>>()

    val gameItemList: LiveData<List<GameItem>>
        get() = mutableGameItemList

    fun loadData() = viewModelScope.launch {
        mutableGameItemList.value = model.getFavoriteGameItemList()
    }
}