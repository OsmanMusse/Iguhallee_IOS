package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.ramaas.iguhallee.MR
import components.FlexibleTopBar
import components.FlexibleTopBarDefaults
import components.ScrollableContent
import components.pullRefresh.PullToRefreshLayout
import components.pullRefresh.PullToRefreshLayoutState
import components.pullRefresh.RefreshIndicatorState
import decompose.home.HomeListComponent
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBarDefaults
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeListView(component: HomeListComponent) {

    val state = component.state.subscribeAsState()

    val mainColor =  Color(72, 134, 255)
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
                containerColor = Color(72, 134, 255)
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
                // 16.dp - 4.dp
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
        PullToRefreshLayout(
            modifier = Modifier.fillMaxSize().padding(it),
            pullRefreshLayoutState =  refreshLayoutState,
            onRefresh = {
                println("REFRESHING ===")
                scope.launch {
                    delay(2000)
                    refreshLayoutState.updateRefreshState(RefreshIndicatorState.Default)
                }
            },
        ) {
            ScrollableContent(state.value.postList,0.dp,component)
        }
    }
}