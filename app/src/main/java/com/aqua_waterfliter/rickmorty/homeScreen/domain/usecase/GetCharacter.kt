package com.aqua_waterfliter.rickmorty.homeScreen.domain.usecase

import com.aqua_waterfliter.rickmorty.core.data.IRepository
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.Character
import javax.inject.Inject

class GetCharacter @Inject constructor(
    private val repository: IRepository,
) {
    suspend operator fun invoke(page: Int): Character {
        return repository.getCharacter(page = page)
    }
}