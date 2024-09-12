package com.aqua_waterfliter.rickmorty.core.data.api.model.mappers

import com.aqua_waterfliter.rickmorty.core.data.api.model.ApiCharacter
import com.aqua_waterfliter.rickmorty.core.data.api.model.ApiCharacterResults
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.Character
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.CharacterLocation
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.CharacterOrigin
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.CharacterResults
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.Info
import javax.inject.Inject

class ApiCharacterMapper @Inject constructor() :
    ApiMapper<ApiCharacter, Character> {
    override fun mapToDomain(apiEntity: ApiCharacter): Character {
        val (info, results) = apiEntity
        return Character(
            info = Info(
                count = info?.count ?: 0,
                pages = info?.pages ?: 0,
                next = info?.next.orEmpty(),
                prev = info?.prev.orEmpty(),
            ),
            results = results.map {
                mapCharacter(it)
            }
        )
    }

    private fun mapCharacter(apiCharacterResults: ApiCharacterResults): CharacterResults {
        return CharacterResults(
            id = apiCharacterResults.id ?: 0,
            name = apiCharacterResults.name.orEmpty(),
            status = apiCharacterResults.status.orEmpty(),
            species = apiCharacterResults.species.orEmpty(),
            gender = apiCharacterResults.gender.orEmpty(),
            origin =
            CharacterOrigin(
                url = apiCharacterResults.origin?.url.orEmpty(),
                name = apiCharacterResults.origin?.name.orEmpty(),
            ),
            location =
            CharacterLocation(
                name = apiCharacterResults.location?.name.orEmpty(),
                url = apiCharacterResults.location?.url.orEmpty(),
            ),
            image = apiCharacterResults.image.orEmpty(),
            episode = apiCharacterResults.episode ?: emptyList(),
            url = apiCharacterResults.url.orEmpty(),
            created = apiCharacterResults.created.orEmpty(),
        )
    }
}