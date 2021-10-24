package com.tolgaocal.rawggamesgallery.home_files

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolgaocal.rawggamesgallery.database_files.GameItem
import kotlinx.coroutines.launch


class HomeViewModel(private val homeModel: HomeModel) : ViewModel() {

    private val mutableGameItemList = MutableLiveData<List<GameItem>>()

    private val mutableSearchedGameItemList = MutableLiveData<List<GameItem>>()

    val gameItemList: LiveData<List<GameItem>>
        get() = mutableGameItemList

    val searchedGameItemList: LiveData<List<GameItem>>
        get() = mutableSearchedGameItemList

    fun loadData() = viewModelScope.launch {
        mutableSearchedGameItemList.value = homeModel.getGameItemList()
        mutableGameItemList.value = homeModel.getGameItemList()
    }

    fun searchGame(searchString: String) = viewModelScope.launch {
        mutableSearchedGameItemList.value = homeModel.getGameItemListBySearch(searchString)
        mutableGameItemList.value = homeModel.getGameItemList()
    }

}