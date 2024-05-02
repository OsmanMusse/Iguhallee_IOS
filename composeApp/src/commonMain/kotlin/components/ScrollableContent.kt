package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingSource
import app.cash.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.ramaas.iguhallee.MR
import decompose.home.HomeListComponent
import dev.icerock.moko.resources.compose.painterResource
import domain.model.Post
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScrollableContent(
    component: HomeListComponent
) {



    val pagingPosts = component.posts.collectAsLazyPagingItems()

//    when(pagingPosts.loadState.append){
//        is androidx.paging.LoadState.Loading -> println("PAGING STATE == LOADING")
//        is androidx.paging.LoadState.NotLoading -> println("PAGING STATE == NOT LOADING")
//        is androidx.paging.LoadState.Error -> println("PAGING STATE == ERROR")
//    }

    println("PAGING COUNT == ${pagingPosts.itemCount}")
//    println("${pagingPosts.itemSnapshotList.forEach {
//        println("POST TITLE == ${it?.title}")
//    }}")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(241, 242, 243))
                .pullRefresh(rememberPullRefreshState(refreshing = true, onRefresh = {})),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 60.dp)

        ) {
            item(span = { GridItemSpan(2) }) {
                ScrollableContentHeader()
            }


            items(pagingPosts.itemCount) { index ->
                val specificPost = pagingPosts[index]
                ScrollableMainContent(specificPost!!,index, onclick = {
                    println("POST CLICKEED == ${index}")
                    component.goToDetailsScreen()
                })
            }
        }

}


@Composable
fun ScrollableMainContent(postData: Post, index: Int,onclick:(index:Int) -> Unit){


    var isBtnActive by remember{ mutableStateOf(false) }
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
            )
            ,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(3.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ){
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data("${postData.postImgs.first()}")
                .build(),
            contentDescription = null,
            imageLoader = ImageLoader(context = LocalPlatformContext.current),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth().height(175.dp).padding(8.dp)

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
            IconButton(
                modifier = Modifier.indication(MutableInteractionSource(),null),
                onClick = {
                    isBtnActive = !isBtnActive
                },
                interactionSource = MutableInteractionSource()
            ){
                Icon(
                    modifier = Modifier.size(22.dp),
                    painter = if(isBtnActive) painterResource(MR.images.love_icon_active) else painterResource(MR.images.love_icon),
                    contentDescription = null,
                    tint = if (isBtnActive) Color(255, 117, 109) else Color(131, 139, 151),
                )
            }
        }
    }

}

@Composable
fun ScrollableContentHeader(){
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
            text = "98 Bollo Bridge Road",
            color = Color(72, 134, 255),
            fontSize = 16.sp
        )
    }
}