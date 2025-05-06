package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.network.AmphibianPhoto
import com.example.amphibians.ui.theme.AmphibiansTheme
import com.example.amphibians.ui.theme.Typography

// 9th re-factoring: change type of amphibiansUiState
@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    // 10th re-factoring: choose screens based on state
    when (amphibiansUiState) {

        is AmphibiansUiState.Loading -> LoadingScreen(modifier.fillMaxSize())

//        is AmphibiansUiState.Success -> ResultScreen(
//            amphibiansUiState.amphibians,
//            modifier.padding(top = contentPadding.calculateTopPadding())
//        )
        // 26th re-factoring: show AmphibianPhotoCard in interim while creating UI to show one photo
//        is AmphibiansUiState.Success -> AmphibianPhotoCard(
//            amphibiansUiState.amphibians,
//            modifier = Modifier.fillMaxSize()
//        )
        // 29th re-factoring: show photo grid
        is AmphibiansUiState.Success -> PhotosGridScreen(
            amphibiansUiState.amphibians,
            modifier.padding(top = contentPadding.calculateTopPadding()))

        is AmphibiansUiState.Error -> ErrorScreen(retryAction, modifier.fillMaxSize())
    }

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * ResultScreen displaying number of amphibians
 */
@Composable
fun ResultScreen(amphibians: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = amphibians)
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.loading_failed)
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        // 31st refactoring: add retry feature to error screen
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun AmphibianPhotoCard(photo: AmphibianPhoto, modifier: Modifier = Modifier) {

    // Card delegates the content of the trailing lambda expression to the Column composable.
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Text(
            text = "${photo.name} (${photo.type})",
            style = Typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom = 8.dp)
        )

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.imgSrc)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.amphibian_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = photo.description,
            style = Typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )

    }

}

@Composable
fun PhotosGridScreen(
    photos: List<AmphibianPhoto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // amphibian photos (and content) fit in one column
        modifier = modifier.padding(horizontal = 4.dp).padding(bottom = 20.dp),
        contentPadding = contentPadding
    ) {
        items(items = photos, key = { photo -> photo.name }) { photo ->
            AmphibianPhotoCard(
                photo,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AmphibiansTheme {
        val previewPhoto = AmphibianPhoto("Preview Toad", "toad", "description", "preview.img.src")
        val previewList = listOf(previewPhoto)
        val amphibiansUiState = AmphibiansUiState.Success(previewList)
        HomeScreen(amphibiansUiState, {}, contentPadding = PaddingValues(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AmphibiansTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AmphibiansTheme {
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    AmphibiansTheme {
        ResultScreen(stringResource(R.string.placeholder_result))
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    AmphibiansTheme {
        // for the key values of the items in the LazyVerticalGrid to be unique,
        // pass the index values of the List()
        val mockData = List(10) { AmphibianPhoto("$it", "type", "description", "imgSrc") }
        PhotosGridScreen(mockData)
    }
}
