package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.TopAppBar
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import components.CoordinatedScroll
import components.CustomBottomAppBar
import components.CustomTopAppBar
import components.FlexibleTopBar
import components.FlexibleTopBarDefaults
import components.ScrollableContent
import components.TopBarCollapsing
import dev.icerock.moko.resources.compose.painterResource
import org.example.iguhallee.MR


private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Transparent

    @Composable
    override fun rippleAlpha() = RippleAlpha(0F, 0F, 0F, 0F)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){

    val toolbarHeightPx = remember { mutableStateOf(0f) }
    val topPanelOffset = remember { mutableStateOf(0f) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        backgroundColor = Color(72, 134, 255),
        modifier = Modifier.fillMaxWidth().fillMaxHeight().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().statusBarsPadding()) {
                FlexibleTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    colors = FlexibleTopBarDefaults.topAppBarColors(
                        containerColor = Color(72, 134, 255)
                    ),
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
                ){
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp).height(50.dp),
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
                            Text("Search Iguhallee", color = Color.LightGray)
                        }
                    }
                }

                Column(modifier = Modifier.fillMaxWidth()){
                    FlexibleTopBar(
                        modifier = Modifier.fillMaxWidth().background(Color.Blue),
                        colors = FlexibleTopBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            scrolledContainerColor = Color.White
                        ),
                        scrollBehavior = scrollBehavior
                    ){
                        // 16.dp - 4.dp
                        Row(
                            modifier = Modifier.fillMaxWidth().height(75.dp).background(Color.Blue)
                                .padding(top = 0.dp, bottom = 0.dp, end = 16.dp - 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    tint = Color(72, 134, 255)
                                )
                                Text("Property")
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    tint = Color(72, 134, 255)
                                )
                                Text("For Sale")
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    tint = Color(72, 134, 255)
                                )
                                Text("For Rent")
                            }


                            Column(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    tint = Color(72, 134, 255)
                                )
                                Text("Land")
                            }


                        }
                    }
                }
            }


        },
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme){
                CustomBottomAppBar()
            }
        },
    ) {
       ScrollableContent()
    }
}