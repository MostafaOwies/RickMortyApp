package com.aqua_waterfliter.rickmorty.core.data

import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.Character


interface IRepository {

    suspend fun getCharacter(page: Int): Character
}