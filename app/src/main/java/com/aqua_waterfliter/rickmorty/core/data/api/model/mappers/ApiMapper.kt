package com.aqua_waterfliter.rickmorty.core.data.api.model.mappers

interface ApiMapper<E, D> {
  fun mapToDomain(apiEntity: E): D
}
