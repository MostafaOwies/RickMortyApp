package com.aqua_waterfliter.rickmorty.homeScreen.presentation

sealed class HomeEvent {
    data object LoadCharacters : HomeEvent()
    data object LoadMoreCharacters : HomeEvent()

}