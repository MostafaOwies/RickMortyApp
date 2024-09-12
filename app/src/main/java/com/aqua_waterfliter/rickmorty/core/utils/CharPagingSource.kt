package com.aqua_waterfliter.rickmorty.core.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.CharacterResults
import com.aqua_waterfliter.rickmorty.homeScreen.domain.usecase.GetCharacter

class CharPagingSource(
    private val getCharacterUseCase: GetCharacter,
) : PagingSource<Int, CharacterResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResults> {
        val page = params.key ?: 1 // Start at page 1 if undefined

        return try {
            val response = getCharacterUseCase(page) // Call the use case with the current page
            val characters = response.results ?: emptyList()

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info?.next != null) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterResults>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
