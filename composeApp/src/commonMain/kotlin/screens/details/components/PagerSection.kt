package screens.details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import data.post.PostDetailState
import decompose.detail.PostDetailComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerSection(
    component: PostDetailComponent,
    state: PostDetailState,
    verticalScroll: ScrollState
) {

    val scope = rememberCoroutineScope()

    val listOfImages = state.post?.postImgs

    val pagerState = rememberPagerState(0,0F, pageCount = { listOfImages?.size ?: 1 })
    var currentPagerPosition = remember { mutableIntStateOf(1) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .graphicsLayer {
                alpha =  -1f / 720.dp.toPx() * verticalScroll.value  + 1
            }
    ){
        HorizontalPager(
            state = pagerState,
            pageSize =  PageSize.Fill,
            key = { listOfImages?.get(it) ?: "" }
        ){ index ->
            val media = listOfImages?.get(index)
            currentPagerPosition.value = pagerState.currentPage + 1
            val imageRequest = ImageRequest
                .Builder(LocalPlatformContext.current)
                .data(media)
                .crossfade(true)
                .build()
            SubcomposeAsyncImage(
                model = imageRequest,
                contentDescription = null,
                onLoading = {},
                onError = {println("IMAGE ERROR MESSAGE == ${it.result.throwable.message}")},
                onSuccess = { println("IMAGE LOADED MESSAGE == ${it.result.image} ") },
                loading = {
                    if(media != listOfImages!![0]) {
                        println("IS NOT FIRST IMAGE === ")
                        ShowLoadingIndicator(
                            size = DpSize(35.dp,35.dp),
                            padding = PaddingValues(top = 0.dp)
                        )
                    }

                },
                imageLoader = ImageLoader(context = LocalPlatformContext.current),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        scope.launch {
                            component.showPagerModal()
                        }
                    },
                contentScale = ContentScale.FillBounds,
            )
        }

        Box(
            modifier = Modifier
                .size(width = 60.dp,30.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.BottomEnd)
                .background(Color.Black.copy(alpha = 0.45f))
        ){
            Text(
                text = "${currentPagerPosition.value}/${listOfImages?.size}",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }



    }
}