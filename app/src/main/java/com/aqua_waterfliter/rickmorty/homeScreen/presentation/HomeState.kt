package com.aqua_waterfliter.rickmorty.homeScreen.presentation

import com.aqua_waterfliter.rickmorty.core.utils.Event
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.Character

data class HomeState(
    val isLoading: Boolean = false,
    val failure: Event<Throwable>? = null,
    val character: Character? = null,
    val pagingCharacter: Character? = null,
    val currentPage: Int = 1,
    val isPaging: Boolean = false,
    val endReached: Boolean = false,
)