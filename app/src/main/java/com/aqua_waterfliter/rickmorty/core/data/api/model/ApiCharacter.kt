package com.aqua_waterfliter.rickmorty.core.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCharacter(
    @field:Json(name = "info") val info: ApiInfo?,
    @field:Json(name = "results") val results: List<ApiCharacterResults>,
)

@JsonClass(generateAdapter = true)
data class ApiInfo(
    @field:Json(name = "count") val count: Int?,
    @field:Json(name = "pages") val pages: Int?,
    @field:Json(name = "next") val next: String?,
    @field:Json(name = "prev") val prev: String?,

    )

@JsonClass(generateAdapter = true)
data class ApiCharacterResults(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "species") val species: String?,
    @field:Json(name = "gender") val gender: String?,
    @field:Json(name = "origin") val origin: ApiCharacterOrigin?,
    @field:Json(name = "location") val location: ApiCharacterLocation?,
    @field:Json(name = "image") val image: String?,
    @field:Json(name = "episode") val episode: List<String>?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "created") val created: String?,
)


@JsonClass(generateAdapter = true)
data class ApiCharacterLocation(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?,
)

@JsonClass(generateAdapter = true)
data class ApiCharacterOrigin(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?,
)