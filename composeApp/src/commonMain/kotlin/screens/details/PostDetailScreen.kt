package screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.ramaas.iguhallee.MR
import data.post.PostDetailState
import decompose.detail.DefaultPagerModal
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import decompose.detail.PostDetailComponent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import kotlinx.coroutines.launch
import screens.details.components.CallButtonSection
import screens.details.components.CollapsingToolbar
import screens.details.components.DateInfoSection
import screens.details.components.DescriptionSection
import screens.details.components.MainInfoSection
import screens.details.components.PagerModal
import screens.details.components.PagerSection
import screens.details.components.ShareListingSection
import screens.details.components.ShowLoadingIndicator
import screens.details.components.UserInfoSection


@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun PostDetailScreen(component: PostDetailComponent) {

    println("Post Detial Component == ${component.postID}")
    val state by component.state.subscribeAsState()
    val modalState by component.modal.subscribeAsState()
    val postList = state.post?.postImgs

    lateinit var modalComponent: DefaultPagerModal


    val scope = rememberCoroutineScope()
    var bottomSheetState = rememberCupertinoSheetState(presentationStyle = PresentationStyle.Fullscreen)
    val verticalScroll = rememberScrollState()

    modalState.child?.instance?.also {
        modalComponent = it
        scope.launch {
            bottomSheetState.show()
        }
    }



    val toolbarHeight = 1046.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // Returning Zero so we just observe the scroll but don't execute it
                return Offset.Zero
            }
        }
    }


    CupertinoBottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CollapsingToolbar(
                collapsedBackgroundColor = Color(72, 134, 255),
                backgroundColor = Color.Transparent,
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                onBackButtonPressed = {  component.navigateBack() }
            )
        },
        scaffoldState = CupertinoBottomSheetScaffoldState(bottomSheetState),
        sheetSwipeEnabled = false,
        sheetDragHandle = null,
        sheetContent = {
                PagerModal(
                    onDismissClicked = {
                        println("MODAL DISMISS MASCUUD ===")
                        modalComponent.onDismissModal()
                         scope.launch {
                             bottomSheetState.hide()
                         }
                    },
                    listOfImages = postList
                )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color(241, 242, 243)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection)
                    .verticalScroll(verticalScroll)
                    .background(Color(241, 242, 243))
                    .weight(1f, false)
            ) {

                PagerSection(component, state, verticalScroll)
                FirstSection(component, state)
                SecondSection(state)

            }
            CallButtonSection(state = state)
        }
    }

}

@Composable
fun FirstSection(component: PostDetailComponent, state: PostDetailState) {

    if (state.isMainContentLoading) ShowLoadingIndicator(DpSize(40.dp,40.dp), PaddingValues(top = 20.dp))

    val postID = state.post?.id

    AnimatedVisibility(
        visible = !state.isMainContentLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(Color.White)
                .padding(start = 15.dp, top = 15.dp, bottom = 10.dp, end = 15.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "${state.post?.title}",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                    )
                    Box(
                        modifier = Modifier
                            .size(43.dp)
                            .clip(CircleShape)
                            .clickable(enabled = false, interactionSource = MutableInteractionSource(), indication = null){}
                            .border(0.5.dp, color = Color.Gray, CircleShape)
                    ) {
                        IconToggleButton(
                            checked = false,
                            onCheckedChange = {},
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                    onClick = {}
                                ),
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable(
                                        enabled = true,
                                        interactionSource = MutableInteractionSource(),
                                        indication = rememberRipple(),
                                        onClick = {
                                            if(state.isPostBookmarked) component.unBookmarkPost(postID!!)
                                            else component.bookmarkPost(postID!!)
                                        }
                                    ),
                                painter = painterResource(
                                    if(state.isPostBookmarked) MR.images.love_icon_active
                                    else MR.images.love_icon
                                ),
                                contentDescription = null,
                                tint = if (state.isPostBookmarked) Color(255, 117, 109) else Color(131, 139, 151),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(27.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "£", fontSize = 20.5.sp)
                    Text(
                        text = "${state.post?.formattedPrice}",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(23.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(MR.images.location_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "${state.post?.district}, ${state.post?.location}",
                        color = Color(0, 127, 176),
                        fontSize = 15.5.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
      }
    }

}

@Composable
fun SecondSection(state: PostDetailState){
    if(!state.isUserProfileLoading && !state.isMainContentLoading){
        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()){
            Column(modifier = Modifier.fillMaxWidth()){
                UserInfoSection(state)
                DescriptionSection(state)
                ShareListingSection()
                MainInfoSection(state)
                DateInfoSection(state)
            }
        }

    } else if(!state.isMainContentLoading){
        ShowLoadingIndicator(DpSize(40.dp,40.dp), PaddingValues(top = 20.dp))
    }
}




