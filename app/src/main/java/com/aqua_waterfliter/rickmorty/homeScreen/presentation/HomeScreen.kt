package com.aqua_waterfliter.rickmorty.homeScreen.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    val state = viewModel.state.collectAsState().value
    val isRefreshing = state.isLoading
    val pullRefreshState = rememberPullToRefreshState()
    val lazyState = rememberLazyListState()
    val characterItems = viewModel.characters.collectAsLazyPagingItems()


    Box(
        modifier = Modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection)
            .padding(top = 110.dp),
    ) {

        LazyColumn(
            state = lazyState,
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(characterItems) {
                it?.let {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top // Align items vertically in the center
                        ) {
                            // Circular image on the left
                            AsyncImage(
                                model = it.image,
                                contentDescription = it.name,
                                modifier = Modifier
                                    .size(200.dp) // Set the desired size for the circular image
                                    .clip(RoundedCornerShape(8.dp)) // Clip the image to a circular shape
                                    .border(
                                        2.dp,
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(8.dp)
                                    ) // Optional: Add border
                            )

                            Spacer(modifier = Modifier.width(12.dp)) // Space between image and text

                            // Column for the name, gender, and status
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                // Name text
                                Text(
                                    text = "${it.name}",
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Spacer(modifier = Modifier.height(8.dp)) // Small space between name and gender/status

                                // Row for gender and status
                                // Gender text
                                Row {
                                    Text(
                                        text = "Gender: ",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "${it.gender}",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp)) // Small space between name and gender/status
                                Row {
                                    Text(
                                        text = "Type: ",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "${it.species}",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp)) // Small space between name and gender/status
                                // Status text
                                Row {
                                    Text(
                                        text = "Status: ",
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Text(
                                        text = "${it.status}",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }

                }
            }
            characterItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        // Initial loading state
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .size(70.dp)
                                        .padding(16.dp)
                                )
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .size(70.dp)
                                        .padding(16.dp)
                                )
                            }
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
                viewModel.onEvent(HomeEvent.LoadCharacters)

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
