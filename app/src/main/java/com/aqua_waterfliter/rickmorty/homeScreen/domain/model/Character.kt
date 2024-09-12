package com.aqua_waterfliter.rickmorty.homeScreen.domain.model


data class Character(
    val info: Info?,
    val results: List<CharacterResults>,
)


data class Info(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?,

    )

data class CharacterResults(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val gender: String?,
    val origin: CharacterOrigin?,
    val location: CharacterLocation?,
    val image: String?,
    val episode: List<String>?,
    val url: String?,
    val created: String?,
)


data class CharacterLocation(
    val name: String?,
    val url: String?,
)

data class CharacterOrigin(
    val name: String?,
    val url: String?,
)