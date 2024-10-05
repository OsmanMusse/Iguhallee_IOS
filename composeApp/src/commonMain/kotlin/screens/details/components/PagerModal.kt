package screens.details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import com.github.panpf.sketch.request.ComposableImageRequest
import com.github.panpf.zoomimage.SketchZoomAsyncImage
import com.github.panpf.zoomimage.compose.zoom.ScrollBarSpec
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetContent
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi

@OptIn(ExperimentalFoundationApi::class, ExperimentalCupertinoApi::class)
@Composable
fun PagerModal(
    onDismissClicked: () -> Unit,
    listOfImages: List<String>?
) {


    val currentPagerPosition = remember { mutableIntStateOf(1) }
    val pagerState = rememberPagerState(0,0F, pageCount = {listOfImages?.size ?: 1})

    CupertinoBottomSheetContent(
        containerColor = Color.Black
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
            ){
                CupertinoButton(
                    colors = CupertinoButtonDefaults.borderedButtonColors(containerColor = Color.Transparent),
                    modifier = Modifier.align(Alignment.TopStart),
                    onClick = { onDismissClicked() }
                ) {
                    Text(
                        text = "Done",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "${currentPagerPosition.value} / ${listOfImages?.size}",
                    color = Color.White,
                    fontSize = 20.sp,
                )
            }


            HorizontalPager(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85F),
                pageSpacing = 10.dp,
                state = pagerState,
                pageSize = PageSize.Fill,
                key = { listOfImages?.get(it) ?: ""}
            ){ index ->
                val media = listOfImages?.get(index)
                currentPagerPosition.value = pagerState.currentPage + 1
                val model = ComposableImageRequest(media){
                    crossfade()
                }

                SketchZoomAsyncImage(
                    request = model,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    scrollBar = ScrollBarSpec(Color.Transparent)
                )


                SubcomposeAsyncImage(
                    model = model,
                    contentDescription = null,
                    loading = {
                        ShowLoadingIndicator(DpSize(55.dp,55.dp), PaddingValues(top = 20.dp))
                    },
                    imageLoader = ImageLoader(LocalPlatformContext.current),
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }




    }
}