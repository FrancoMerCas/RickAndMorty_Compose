package com.example.rickandmortyincompose.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyincompose.CharacterListQuery
import com.example.rickandmortyincompose.domain.usecase.GetCharactersUseCase
import com.example.rickandmortyincompose.ui.utils.Const.FIRST_PAGE

class CharactersPaging(
    private val getCharactersUseCase: GetCharactersUseCase
) : PagingSource<Int, CharacterListQuery.Result>(){

    override fun getRefreshKey(state: PagingState<Int, CharacterListQuery.Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterListQuery.Result> {
        try {
            val currentLoadingPage = params.key ?: FIRST_PAGE
            val response = getCharactersUseCase.invoke(currentLoadingPage)
            val responseItem = mutableListOf<CharacterListQuery.Result>()
            val responseList = response.data?.characters?.results ?: emptyList()
            responseItem.addAll(responseList.filterNotNull())

            val nextKey =
                if (response.data?.characters?.info?.next == null) {
                    null
                } else {
                    currentLoadingPage.plus(1)
                }

            val prevKey = if(currentLoadingPage == 1) {
                null
            } else {
                currentLoadingPage
            }

            return LoadResult.Page(
                data = responseItem,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}