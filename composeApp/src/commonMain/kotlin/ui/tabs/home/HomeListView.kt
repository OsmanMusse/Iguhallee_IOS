package ui.tabs.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.collectAsLazyPagingItems
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.ramaas.iguhallee.MR
import components.FlexibleTopBar
import components.FlexibleTopBarDefaults
import screens.home.ScrollableContent
import components.pullRefresh.PullToRefreshLayout
import components.pullRefresh.PullToRefreshLayoutState
import components.pullRefresh.RefreshIndicatorState
import decompose.home.HomeListComponent
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoActivityIndicator
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBarDefaults
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCupertinoApi::class)

@Composable
fun HomeListView(component: HomeListComponent) {

    val state by component.state.subscribeAsState()
    val pagingPosts = component.posts.collectAsLazyPagingItems()

    val mainColor =  Color(71, 134, 255)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val refreshLayoutState = remember { PullToRefreshLayoutState({"RADSADSA"}) }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
             CupertinoTopAppBar(
                 title = {},
                 colors = CupertinoTopAppBarDefaults.topAppBarColors(mainColor),
                 isTranslucent = false
             )
             Column(modifier = Modifier.fillMaxWidth().statusBarsPadding()) {
              FlexibleTopBar(
                modifier = Modifier.fillMaxWidth(),
                colors = FlexibleTopBarDefaults.topAppBarColors(
                containerColor = mainColor
                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
             ) {
             Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp).height(47.dp),
                shape = RoundedCornerShape(5.dp),
                backgroundColor = Color.White
             ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(MR.images.search_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 10.dp).size(30.dp, 30.dp),
                        tint = Color.LightGray
                    )

                    Spacer(modifier = Modifier.width(15.dp))
                    Text("Search Iguhallee", color = Color.LightGray, fontSize = 17.sp)
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth()){
            FlexibleTopBar(
                modifier = Modifier.fillMaxWidth().background(Color.White),
                colors = FlexibleTopBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
                scrollBehavior = scrollBehavior
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().height(80.dp).background(Color.White)
                        .padding(top = 0.dp, bottom = 0.dp, end = 16.dp - 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(MR.images.property_icon),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(0.45F),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(11.dp))
                        Text(text = "Property", fontSize = 13.5.sp)
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(MR.images.sale_tag),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(0.5F),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(11.dp))
                        Text(text ="For Sell",fontSize = 13.5.sp)
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(MR.images.land_free_icon),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(0.57F),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text ="Land",fontSize = 13.5.sp)
                    }


                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter= painterResource(MR.images.rent_tag),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(0.55F),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(text ="For Rent",fontSize = 13.5.sp)
                    }


                }
            }
        }
    }
        }
    ) {
        AnimatedVisibility(
                visible =  pagingPosts.loadState.refresh is LoadStateLoading && state.isInitialLoad,
                enter = fadeIn(),
                exit = fadeOut()
        ){
            Column(
                modifier = Modifier.fillMaxSize().background(Color(241, 242, 243)).padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CupertinoActivityIndicator(modifier = Modifier.size(20.dp).offset(y = 15.dp))
            }
        }

        AnimatedVisibility(
            visible = pagingPosts.loadState.refresh is LoadStateNotLoading || pagingPosts.loadState.refresh is LoadStateError || (pagingPosts.loadState.refresh is LoadStateLoading && !state.isInitialLoad),
            enter = fadeIn(),
            exit = fadeOut()
        ){
            PullToRefreshLayout(
                modifier = Modifier.fillMaxSize().padding(it),
                pullRefreshLayoutState = refreshLayoutState,
                onRefresh = {
                    println("REFRESHING ===")
                    pagingPosts.refresh()
                    scope.launch {
                        delay(2000)
                        refreshLayoutState.updateRefreshState(RefreshIndicatorState.Default)
                    }
                },
            ) {
                ScrollableContent(component,pagingPosts)
            }
        }

    }
}
