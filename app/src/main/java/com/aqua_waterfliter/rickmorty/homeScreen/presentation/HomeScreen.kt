package com.aqua_waterfliter.rickmorty.homeScreen.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.aqua_waterfliter.rickmorty.core.utils.ComposableLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
           // viewModel.onEvent(HomeEvent.LoadCharacters)
        }
    }

    val state = viewModel.state.collectAsState().value
    val character = state.character?.results
    val isRefreshing = state.isLoading
    val pullRefreshState = rememberPullToRefreshState()
    val lazyState = rememberLazyListState()
    val characterItems = viewModel.characters.collectAsLazyPagingItems()


    Box(
        modifier = Modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {

        LazyColumn(
            state = lazyState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(characterItems) {
                it?.let {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            AsyncImage(
                                model = it.image,
                                contentDescription = it.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f) // Keep the image square
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Name: ${it.name}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Gender: ${it.gender}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Status: ${it.status}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            characterItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        // Initial loading state
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(16.dp)
                            )
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        // Pagination loading state
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(16.dp)
                            )
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = characterItems.loadState.refresh as LoadState.Error
                        item {
                            Text("Error: ${error.error.localizedMessage}", color = Color.Red)
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = characterItems.loadState.append as LoadState.Error
                        item {
                            Text("Error: ${error.error.localizedMessage}", color = Color.Red)
                        }
                    }
                }
            }
        }

        if (pullRefreshState.isRefreshing) {
            LaunchedEffect(true) {
               // viewModel.onEvent(HomeEvent.LoadCharacters)

            }
        }
        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullRefreshState.startRefresh()
            } else {
                pullRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}