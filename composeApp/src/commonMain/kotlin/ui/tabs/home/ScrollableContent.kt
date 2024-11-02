package screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.ramaas.iguhallee.MR
import decompose.home.HomeListComponent
import dev.icerock.moko.resources.compose.painterResource
import domain.model.HomeScreenState
import domain.model.Post
import io.github.alexzhirkevich.cupertino.CupertinoAlertDialog
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.default


@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun ScrollableContent(
    component: HomeListComponent, pagingPosts: LazyPagingItems<Post>
) {

    val errorMsg = remember { mutableStateOf("SOME ERROR") }

    val shouldShowAlertDialog = remember { mutableStateOf(false) }
    val state by component.state.subscribeAsState()

    val refreshState = pagingPosts.loadState.refresh is LoadStateLoading

    LaunchedEffect(state.currentCity) {
        println("REFRESH STATE WHEN STATE CHANGE === ${refreshState}")
        if (!state.isInitialLoad) {
            println("REFRESH DATA BECAUSE OF LOCATION CHANGE === ")
            component.refreshPosts()
        }
    }


    LaunchedEffect(pagingPosts.loadState) {
        if (pagingPosts.loadState.append is LoadStateError) {
            println("ERROR == 1")
            errorMsg.value = "${(pagingPosts.loadState.append as LoadStateError).error.message}"
            shouldShowAlertDialog.value = true
        } else if (pagingPosts.loadState.refresh is LoadStateError) {
            errorMsg.value = "${(pagingPosts.loadState.refresh as LoadStateError).error.message}"
            println("ERROR == 2 msg = ${errorMsg.value}")
            when (errorMsg.value) {
                PagingError.INTERNET_CONNECTION.errorMsg -> {
                    shouldShowAlertDialog.value = true
                }

                PagingError.QUERY_NOT_FOUND_FROM_DATABASE.errorMsg -> {
                    component.emptyPagingData()
                }
            }
            state.isInitialLoad = false
        } else if (pagingPosts.loadState.refresh is LoadStateNotLoading) {
            println("ERROR == 3 STATE == ${state.isInitialLoad}")
            state.isInitialLoad = false
        }
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().navigationBarsPadding().background(Color(241, 242, 243)),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {

        item(span = { GridItemSpan(maxLineSpan) }) {

            println("RELOAD THE FOLLOWING SECTION === ")
            ScrollableContentHeader(state, component)
        }

        items(pagingPosts.itemCount) { index ->
            val specificPost = pagingPosts[index]
            val isLastItem = pagingPosts.itemCount == index + 1

            ScrollableMainContent(
                state = state,
                postData = specificPost!!,
                modifier = Modifier.padding(bottom = if (!isLastItem) 28.dp else 0.dp),
                index = index,
                onclick = {
                    component.goToDetailsScreen(specificPost?.id)
                }
            )
        }
    }


    if (shouldShowAlertDialog.value) {
        CupertinoAlertDialog(buttons = {
            default(onClick = {
                println("CANCEL BUTTON CLICKED ===")
                shouldShowAlertDialog.value = false
            },
                enabled = shouldShowAlertDialog.value,
                title = { Text("OK") })
        }, onDismissRequest = { },
            title = { },
            message = {
                CupertinoText(
                    text = "${errorMsg.value}",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold
                )
            })
    }


}


@Composable
fun ScrollableMainContent(
    modifier: Modifier = Modifier,
    postData: Post,
    index: Int,
    onclick: (index: Int) -> Unit,
    state: HomeScreenState
) {

    println("STATE CHANGED ===")
    var isLoveToggled by rememberSaveable { mutableStateOf(state.bookmarkedPosts.contains(postData.id)) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onclick(index) }
    ) {

        Box(
            modifier = Modifier.size(35.dp).align(Alignment.TopEnd)
                .offset(x = (-10).dp, y = (10).dp).clip(RoundedCornerShape(50.dp))
                .background(Color(235, 237, 239)).zIndex(1F),
        ) {
            Icon(
                painter = if (!isLoveToggled) painterResource(MR.images.love_icon_new) else painterResource(
                    MR.images.love_icon_active_new
                ),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center).offset(x = 0.dp, y = 2.dp).clickable {
                    isLoveToggled = !isLoveToggled
                },
                tint = Color.Unspecified
            )
        }

        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data("${postData.postImgs.first()}").build(),
                contentDescription = null,
                imageLoader = ImageLoader(context = LocalPlatformContext.current),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth().height(180.dp),
                onState = { it.painter },
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 8.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = postData.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "£${postData.formattedPrice}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }


}

@Composable
fun ScrollableContentHeader(state: HomeScreenState, component: HomeListComponent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 3.dp, horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "In your area",
            color = Color(31, 47, 59),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Icon(
                painter = painterResource(MR.images.location_icon_new1),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier
                    .clickable(interactionSource = MutableInteractionSource(), indication = null)
                    { component.goToLocationScreen("${state.currentCity}") },
                text = "${state.currentCity}",
                textDecoration = TextDecoration.Underline,
                letterSpacing = 1.5.sp,
                color = Color(72, 134, 255),
                fontSize = 15.5.sp,
            )
        }

    }

}


