package com.aqua_waterfliter.rickmorty.core.data.api

import com.aqua_waterfliter.rickmorty.core.data.api.model.ApiCharacter
import com.aqua_waterfliter.rickmorty.core.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET(Constants.CHARACTER)
    suspend fun getCharacter(@Query("page") page: Int): ApiCharacter
}