package com.aqua_waterfliter.rickmorty.core.data

import com.aqua_waterfliter.rickmorty.core.data.api.CharacterApi
import com.aqua_waterfliter.rickmorty.core.data.api.model.mappers.ApiCharacterMapper
import com.aqua_waterfliter.rickmorty.core.utils.NetworkException
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.Character
import retrofit2.HttpException
import javax.inject.Inject

class Repository @Inject constructor(
    private val characterApi: CharacterApi,
    private val apiCharacterMapper: ApiCharacterMapper,
) : IRepository {

    override suspend fun getCharacter(
        page: Int,
    ): Character {
        try {
            return apiCharacterMapper.mapToDomain(
                characterApi.getCharacter(
                    page = page
                )
            )
        } catch (e: HttpException) {
            throw handleException(exception = e)
        }
    }

    private fun handleException(exception: HttpException): Exception {
        return when (exception.code()) {
            422 -> NetworkException(
                code = exception.code()
            )

            else -> {
                throw NetworkException(
                    message = exception.message ?: "Code ${exception.code()}",
                    code = exception.code()
                )
            }
        }
    }
}