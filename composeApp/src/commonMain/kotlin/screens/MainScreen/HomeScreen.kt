package screens.MainScreen

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.FlexibleTopBar
import components.FlexibleTopBarDefaults
import components.ScrollableContent
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetContent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoNavigationBar
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBarDefaults
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.ramaas.iguhallee.MR
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.orderBy
import dev.gitlive.firebase.firestore.where
import domain.model.Post
import domain.model.PostStatus
import components.pullRefresh.PullToRefreshLayout
import components.pullRefresh.PullToRefreshLayoutState
import components.pullRefresh.RefreshIndicatorState
import navigation.HomeScreenComponent


private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Transparent

    @Composable
    override fun rippleAlpha() = RippleAlpha(0F, 0F, 0F, 0F)
}


suspend fun getUsers(): List<Post> {
    val firebaseFirestore = Firebase.firestore
    try {
        val ref = firebaseFirestore.collection("Posts")
        val query = ref.orderBy("datePosted", Direction.DESCENDING)
            .where {
                "location" equalTo "Garowe"
                "status" equalTo PostStatus.APPROVED.text
            }
        return query.get().documents.map {
            it.data()
        }
    } catch (e: Exception) {
        println("ERROR FIREBASE == ${e.message}")
        throw e
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeScreenComponent) {

    var list by rememberSaveable{ mutableStateOf(listOf<Post>()) }
    println("Fetch Posts Size === ${list.size}")
    LaunchedEffect(Unit){
        if(list.isEmpty()){
            println("Fetch Posts === ${list.size}")
            list = getUsers()
        }

    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    var bottomsheetState = rememberCupertinoSheetState(presentationStyle = PresentationStyle.Fullscreen)
    val refreshLayoutState = remember { PullToRefreshLayoutState({"RADSADSA"}) }


    CupertinoBottomSheetScaffold(
        scaffoldState = CupertinoBottomSheetScaffoldState(bottomsheetState),
        topBar = {
            CupertinoTopAppBar(
                title = {},
                colors = CupertinoTopAppBarDefaults.topAppBarColors(
                    Color(72, 134, 255),
                ),
                isTranslucent = false
            )
            Column(modifier = Modifier.fillMaxWidth().statusBarsPadding()) {
                FlexibleTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    colors = FlexibleTopBarDefaults.topAppBarColors(
                        containerColor = Color(72, 134, 255)
                    ),
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
                ){
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
        },
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme){
                CupertinoNavigationBar() {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(MR.images.home_icon_lineal),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(0.50F).offset(y = 6.dp),
                                tint = Color(190, 190, 190),
                            )
                        },
                        onClick = {},
                        selected = false,
                        label = {
                            Text(
                                text ="Home",
                                fontSize = 12.5.sp,
                                color = Color(158, 158, 158)
                            )
                        }
                    )

                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(MR.images.account_icon),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(0.50F).offset(y = 4.dp),
                                tint = Color(190, 190, 190)
                            )
                        },
                        onClick = {},
                        selected = false,
                        label = {
                            Text(
                                text ="My Iguhallee",
                                fontSize = 12.5.sp,
                                color = Color(158, 158, 158)
                            )
                        }
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(MR.images.camera_icon_linear),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(0.50F).offset(y = 4.dp),
                                tint = Color.Unspecified
                            )
                        },
                        onClick = {
                            println("SHOW BOTTOM SHEET")
                            scope.launch {
                                bottomsheetState.expand()
                            }

                        },
                        selected = false,
                        label = {
                            Text(
                                text = "Post",
                                fontSize = 12.5.sp,
                                color = Color(158, 158, 158)
                            )
                        }
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(MR.images.favourite_icon),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(0.45F).offset(y = 3.dp),
                                tint = Color(190, 190, 190)
                            )
                        },
                        onClick = {},
                        selected = false,
                        label = {
                            Text(
                                text = "Saved",
                                fontSize = 12.5.sp,
                                color = Color(158, 158, 158)
                            )
                        }
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(MR.images.setting_icon_linear),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(0.5F).offset(y = 3.dp),
                                tint = Color(190, 190, 190)
                            )
                        },
                        onClick = {},
                        selected = false,
                        label = {
                            Text(
                                text = "Settings",
                                fontSize = 12.5.sp,
                                color = Color(158, 158, 158)
                            )
                        }
                    )
                }
            }
        },
        sheetDragHandle = {},
        sheetSwipeEnabled = false,
        sheetContent = {
            CupertinoBottomSheetContent {
                CupertinoButton(
                    modifier = Modifier.padding(top = 50.dp),
                    onClick = {
                        scope.launch {
                            bottomsheetState.hide()
                        }

                    }
                ){
                    Text(
                        text = "Hello world",
                    )
                }
            }

        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ){
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
            ScrollableContent(list,0.dp,component)
        }

    }

}
