package screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconToggleButton
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import util.NoRippleTheme
import io.github.alexzhirkevich.cupertino.CupertinoAlertDialog
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.default


@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun ScrollableContent(
    component: HomeListComponent,
    pagingPosts: LazyPagingItems<Post>
) {

    val errorMsg = remember { mutableStateOf("SOME ERROR") }

    val shouldShowAlertDialog = remember { mutableStateOf(false) }
    val state by component.state.subscribeAsState()

    val refreshState = pagingPosts.loadState.refresh is LoadStateLoading

    LaunchedEffect(state.currentCity){
        println("REFRESH STATE WHEN STATE CHANGE === ${refreshState}")
        if (!state.isInitialLoad) {
            println("REFRESH DATA BECAUSE OF LOCATION CHANGE === ")
            component.refreshPosts()
        }
    }


    LaunchedEffect(pagingPosts.loadState){
        if(pagingPosts.loadState.append is LoadStateError){
            println("ERROR == 1")
            errorMsg.value = "${(pagingPosts.loadState.append as LoadStateError).error.message}"
            shouldShowAlertDialog.value = true
        }
        else if(pagingPosts.loadState.refresh is LoadStateError){
            errorMsg.value = "${(pagingPosts.loadState.refresh as LoadStateError).error.message}"
            println("ERROR == 2 msg = ${errorMsg.value}")
            when(errorMsg.value){
                PagingError.INTERNET_CONNECTION.errorMsg -> {
                    shouldShowAlertDialog.value = true
                }
                PagingError.QUERY_NOT_FOUND_FROM_DATABASE.errorMsg -> {
                    component.emptyPagingData()
                }
            }
            state.isInitialLoad = false
        }

        else if (pagingPosts.loadState.refresh is LoadStateNotLoading){
            println("ERROR == 3 STATE == ${state.isInitialLoad}")
            state.isInitialLoad = false
        }
    }




    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(241, 242, 243)),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 60.dp)
    ) {

        item(span = { GridItemSpan(2) }) {
            println("RELOAD THE FOLLOWING SECTION === ")
            ScrollableContentHeader(state,component)
        }

        items(pagingPosts.itemCount) { index ->
            val specificPost = pagingPosts[index]
            ScrollableMainContent(
                state = state,
                component = component,
                postData =  specificPost!!,
                index = index,
                onclick = {
                    component.goToDetailsScreen(specificPost?.id)
                }
            )

        }
    }


    if(shouldShowAlertDialog.value){
        CupertinoAlertDialog(
            buttons = {
                default(
                    onClick = {
                        println("CANCEL BUTTON CLICKED ===")
                        shouldShowAlertDialog.value = false
                    },
                    enabled = shouldShowAlertDialog.value,
                    title = { Text("OK") }
                )
            },
            onDismissRequest = { },
            title = { },
            message = {
                CupertinoText(
                    text = "${errorMsg.value}",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        )
    }


}


@Composable
fun ScrollableMainContent(
    component: HomeListComponent,
    postData: Post,
    index: Int,
    onclick: (index: Int) -> Unit,
    state: HomeScreenState
){

    println("STATE CHANGED ===")
    var isLoveToggled by rememberSaveable { mutableStateOf(state.bookmarkedPosts.contains(postData.id)) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    onclick(index)
                }
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(3.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ){
        println("FIRST IMAGE == ${postData.postImgs.first()}")
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data("${postData.postImgs.first()}")
                .build(),
            contentDescription = null,
            imageLoader = ImageLoader(context = LocalPlatformContext.current),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth().height(175.dp).padding(8.dp),
            onState = { it.painter},
        )
        Text(
            modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp,end = 12.dp, top = 0.dp , bottom = 12.dp),
            text = postData.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 7.dp,end = 0.dp, top = 0.dp , bottom = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "£",fontSize = 14.5.sp)
            Text(text = postData.formattedPrice,fontSize = 20.sp,fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(1f))
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme){

                IconToggleButton(
                    checked = isLoveToggled || state.bookmarkedPosts.contains(postData.id),
                    onCheckedChange = {
                        isLoveToggled = !isLoveToggled
                        if (isLoveToggled) component.savePost(postData.id)
                        else component.unsavePost(postData.id)
                    }
                ){
                    Icon(
                        modifier = Modifier.size(22.dp),
                        painter = if(isLoveToggled || state.bookmarkedPosts.contains(postData.id)) painterResource(MR.images.love_icon_active) else painterResource(MR.images.love_icon),
                        contentDescription = null,
                        tint = if (isLoveToggled || state.bookmarkedPosts.contains(postData.id)) Color(255, 117, 109) else Color(131, 139, 151),
                    )
                }
            }


        }
    }

}

@Composable
fun ScrollableContentHeader(state: HomeScreenState, component: HomeListComponent) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp).height(23.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(

            text = "In your area",
            color = Color(120, 120, 120),
            fontSize = 15.sp
        )
        Text(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ){ component.goToLocationScreen("${state.currentCity}")  },
            text = "${state.currentCity}",
            color = Color(72, 134, 255),
            fontSize = 16.sp
        )
    }

}


