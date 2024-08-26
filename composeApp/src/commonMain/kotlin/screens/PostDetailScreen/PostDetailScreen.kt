package screens.PostDetailScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.crossfade
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.github.panpf.sketch.request.ComposableImageRequest
import com.github.panpf.zoomimage.SketchZoomAsyncImage
import com.github.panpf.zoomimage.compose.zoom.ScrollBarSpec
import com.ramaas.iguhallee.MR
import core.Constants.DEFAULT_MINIMUM_TEXT_LINE
import data.post.PostDetailState
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoActivityIndicator
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import decompose.detail.PostDetailComponent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetContent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults
import io.github.alexzhirkevich.cupertino.CupertinoSheetState
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import kotlinx.coroutines.launch
import util.MakePhoneCall


private val barHeight = 56.dp
private val navigationIconStartPadding = 4.dp


@OptIn(ExperimentalCupertinoApi::class, ExperimentalFoundationApi::class)
@Composable
fun PostDetailScreen(component: PostDetailComponent) {

    println("Post Detial Component == ${component.postID}")
    val state by component.state.subscribeAsState()
    val scope = rememberCoroutineScope()

    var bottomSheetState = rememberCupertinoSheetState(presentationStyle = PresentationStyle.Fullscreen)
    val verticalScroll = rememberScrollState()

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
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CollapsingToolbarBase(
                toolbarHeading = "",
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                collapsedBackgroundColor = Color(72, 134, 255),
                backgroundColor = Color.Transparent
            ){
                Box(modifier = Modifier.fillMaxSize()){
                    Icon(
                        painter = painterResource(MR.images.back_icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(start = 5.dp).rotate(180F).align(Alignment.CenterStart)
                    )
                }
            }
        },
        scaffoldState = CupertinoBottomSheetScaffoldState(bottomSheetState),
        sheetSwipeEnabled = false,
        sheetDragHandle = null,
        sheetContent = {

            val currentPagerPosition = remember { mutableIntStateOf(1) }
            val listOfImages = state.post?.postImgs
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
                            onClick = {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            }
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
    ){

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color(241, 242, 243)),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection)
                    .verticalScroll(verticalScroll)
                    .background(Color(241, 242, 243))
                    .weight(1f, false)
            ){

                PagerSection(bottomSheetState,state,verticalScroll)
                FirstSection(component,state)
                SecondSection(state)

           }
            CallButtonSection(state = state)
        }
    }


}







@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerSection(
    imageFullScreenState: CupertinoSheetState,
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
            val imageRequest = coil3.request.ImageRequest
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
                            imageFullScreenState.show()
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

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun ShowLoadingIndicator(size:DpSize,padding: PaddingValues){
    Box(modifier = Modifier.fillMaxWidth()){
        CupertinoActivityIndicator(
            modifier = Modifier
                .size(size)
                .padding(padding)
                .align(Alignment.Center)
        )
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

@Composable
fun UserInfoSection(state: PostDetailState) {
    val user = state.user

    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(70.dp)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Spacer(modifier = Modifier.width(75.dp))
        Column(modifier = Modifier){
            Text(
                text = "${user?.profileName} (${user?.postCount} listing)",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Member since ${user?.memberSince}",
                color = Color(192,192,192),
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(MR.images.back_icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(16.5.dp)
        )
    }
}

@Composable
fun DescriptionSection(state: PostDetailState){

    val post = state.post

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(start = 15.dp,top = 15.dp, bottom =  10.dp, end = 15.dp)
    ){
        ExpandableText(
            text = "${post?.descriptions}"

        )
    }
}

@Composable
fun ShareListingSection(){
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Share This Listing",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(33,33,33)
        )
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ){
            Box(
                modifier = Modifier
                    .size(33.dp)
                    .clip(CircleShape)
                    .background(Color(58,88,155))
            )

            Box(
                modifier = Modifier
                    .size(33.dp)
                    .clip(CircleShape)
                    .background(Color(29,161,242))
            )

            Box(
                modifier = Modifier
                    .size(33.dp)
                    .clip(CircleShape)
                    .background(Color(117,117,117))
            )

        }
    }

}

@Composable
fun MainInfoSection(state:PostDetailState){

    val user = state.user

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "Seller type",
            color = Color(192,192,192),
            fontSize = 18.sp
        )
        Text(
            text = "${user?.usertype?.name}",
            color = Color(33,33,33),
            fontSize = 18.sp
        )
    }
}

@Composable
fun DateInfoSection(state: PostDetailState){

    val post = state.post

    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier.padding(start = 15.dp)
        ){
            Icon(
                painter = painterResource(MR.images.clock_icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(15.dp)
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = "1 week ago",
                color = Color(192,192,192),
                fontSize = 14.5.sp
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 27.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "ID: ${post?.id?.times(-1)}",
                color = Color(192,192,192),
                fontSize = 14.5.sp
            )

            Text(
                text = "Report",
                color = Color(0,127,176),
                fontWeight = FontWeight.Normal,
                fontSize = 17.5.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CallButtonSection(state: PostDetailState) {

    if (!state.isMainContentLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
                .border(1.dp,Color(216,214,217,))
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
        ){
            Button(
                onClick = { MakePhoneCall("") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(231,84,98)
                )

            ) {
                Text(
                    text = "Call",
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            }
        }
    }

}

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
    textAlign: TextAlign? = null
){
    var isExpanded by remember { mutableStateOf(false)}
    var isClickable by remember {( mutableStateOf(false))}
    var lastCharIndex by remember {mutableStateOf(0)}
    var textMaxLine by remember { mutableStateOf(0)}

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = isClickable,
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
            }
            .then(modifier)
    ){

        Column() {
            Text(
                modifier = textModifier
                    .fillMaxWidth()
                    .animateContentSize(),
                text = buildAnnotatedString {
                    if (isClickable) {
                        if (isExpanded) {
                            append(text)
                        } else {
                            val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                                .dropLastWhile { it.isWhitespace() || it == '.' }
                            append("$adjustText...")
                        }
                    } else {
                        append(text)
                    }
                },
                maxLines = if (isExpanded) 87 else collapsedMaxLine,
                fontStyle = fontStyle,
                onTextLayout = { textLayoutResult ->
                    if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                        println("TextCount == ${textLayoutResult.lineCount}")
                        isClickable = true
                        textMaxLine = textLayoutResult.lineCount
                        lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                    }
                },
                style = style,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = textAlign
            )

            if(textMaxLine > 8){
                Button(
                    onClick = {
                        isExpanded = !isExpanded
                    },
                    elevation = ButtonDefaults.buttonElevation(0.dp,0.dp,0.dp,0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        disabledContainerColor = Color.White
                    ),
                    shape = RectangleShape
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = if (isExpanded) "Read less" else "Read more",
                            color = Color(60,129,174,255),
                            fontSize = 17.5.sp,
                            modifier = Modifier.padding(top = if(!isExpanded)20.dp else 0.dp)
                        )
                    }

                }
            }
        }
    }

}


@Composable
fun CollapsingToolbarBase(
    modifier: Modifier = Modifier,
    toolbarHeading: String,
    onBackButtonPressed: () -> Unit = { },
    contentAlignment: Alignment = Alignment.Center,
    shape: Shape = RoundedCornerShape(
        bottomEnd = 0.dp,
        bottomStart = 0.dp
    ),
    collapsedBackgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    toolbarHeight: Dp,
    minShrinkHeight: Dp = 100.dp,
    toolbarOffset: Float,
    content: @Composable BoxScope.() -> Unit
) {
    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.9f)
    }

    val scrollDp = toolbarHeight + toolbarOffset.dp


    val animatedTitleAlpha by animateFloatAsState(
        targetValue = if (toolbarHeading.isNotBlank()) {
            if (scrollDp <= minShrinkHeight + 20.dp) 1f else 0f
        } else 0f,
        animationSpec = tween(300, easing = LinearOutSlowInEasing)
    )
    val animatedColor by animateColorAsState(
        targetValue = if (scrollDp < minShrinkHeight + 20.dp) collapsedBackgroundColor
        else backgroundColor,
        animationSpec = tween(300, easing = LinearOutSlowInEasing)
    )

    LaunchedEffect(key1 = animatedProgress) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(500, easing = FastOutSlowInEasing)
        )
    }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)

            .background(
                color = animatedColor,
                shape = shape
            )
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackButtonPressed,
                modifier = Modifier
                    .padding(30.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Text(
                text = toolbarHeading,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(
                    alpha = animatedTitleAlpha
                ),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(10.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .alpha(1 - animatedTitleAlpha),
            contentAlignment = contentAlignment,
            content = content
        )
    }
}
