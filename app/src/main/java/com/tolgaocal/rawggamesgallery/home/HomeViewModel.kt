package com.tolgaocal.rawggamesgallery.home

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

    fun searchByName(searchString: String) = viewModelScope.launch {
        mutableSearchedGameItemList.value = homeModel.getGameItemListByNameSearch(searchString)
        mutableGameItemList.value = homeModel.getGameItemList()
    }

}