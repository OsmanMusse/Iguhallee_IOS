package screens.PostDetailScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.ramaas.iguhallee.MR
import core.Constants.DEFAULT_MINIMUM_TEXT_LINE
import decompose.home.HomeScreenComponent
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import kotlin.math.roundToInt


@OptIn(ExperimentalCupertinoApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(rootComponent: HomeScreenComponent){
    // CollapsingToolbar Implementation
    val toolbarHeight = 300.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(  0f) }
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
    CupertinoScaffold(
        topBar = {
            CollapsingToolbarBase(
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                toolbarHeading = "Something",
                collapsedBackgroundColor = Color.Black,
                backgroundColor = Color.Transparent,
            ){}
//            CupertinoTopAppBar(
//                isTranslucent = false,
//                isTransparent = true,
//                title = {},
//                navigationIcon = {
//                    Icon(
//                        modifier = Modifier
//                            .clickable(
//                                interactionSource = MutableInteractionSource(),
//                                indication = null,
//                                onClick = { rootComponent.onBack() }
//                        ),
//                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
//                        contentDescription = null,
//                        tint = Color.White
//                    )
//                },
//            )
        }
    ){
        Column(
            modifier = Modifier.fillMaxHeight().nestedScroll(nestedScrollConnection),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color(241, 242, 243))
                    .weight(1f, false)
            ){
                PagerSection()
                FirstSection()
                SecondSection()
                DescriptionSection()
                ShareListingSection()
                MainInfoSection()
                DateInfoSection()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.White)
                    .border(1.dp,Color(216,214,217,))
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
            ){
                Button(
                    onClick = {},
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
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerSection() {

    val listOfImages = listOf(
        "https://imagedelivery.net/ePR8PyKf84wPHx7_RYmEag/d8be5b82-5498-4043-694d-043fb930f900/86",
        "https://imagedelivery.net/ePR8PyKf84wPHx7_RYmEag/93a490ab-8194-46c3-882e-be93e7462b00/86"
    )
    val pagerState = rememberPagerState(0,0F, pageCount = { 2 })
    var currentPagerPosition = remember { mutableIntStateOf(1) }

    Box(modifier = Modifier.fillMaxWidth().height(380.dp)){
        HorizontalPager(
            state = pagerState,
            pageSize =  PageSize.Fill,
            key = { listOfImages[it]}
        ){ index ->
            val media = listOfImages[index]
            currentPagerPosition.value = pagerState.currentPage + 1
            AsyncImage(
                model = media,
                contentDescription = null,
                imageLoader = ImageLoader(context = LocalPlatformContext.current),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
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
                text = "${currentPagerPosition.value}/${listOfImages.size}",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }



    }
}

@Composable
fun FirstSection(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .background(Color.White)
            .padding(start = 15.dp,top = 15.dp, bottom =  10.dp, end = 15.dp)
    ){
        Column(modifier = Modifier.fillMaxWidth()){

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ){
                Text(
                    text = "3 bedroom flat in Fulham Palace Road, London, W6",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                )
                Box(
                    modifier = Modifier
                        .size(43.dp)
                        .clip(CircleShape)
                        .border(0.5.dp, color = Color.Gray, CircleShape)
                ){
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
                    ){
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(MR.images.love_icon),
                            contentDescription = null,
                            tint = Color(131, 139, 151),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(27.dp))

            Row(modifier = Modifier.fillMaxWidth()){
                Text(text = "£", fontSize = 20.5.sp)
                Text(
                    text = "90.00",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(23.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(MR.images.location_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(15.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "Acton",
                    color = Color(0,127,176),
                    fontSize = 15.5.sp,
                    fontWeight = FontWeight.Normal
                )
            }



        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun SecondSection(){
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
                text = "Alex (1 listing)",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Member since 2014",
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
fun DescriptionSection(){

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(start = 15.dp,top = 15.dp, bottom =  10.dp, end = 15.dp)
    ){
        ExpandableText(
            text = "\uD83C\uDFE0 Double Room near Uxbridge Town Centre & Brunel University \uD83C\uDFE0\n" +
                    "\n" +
                    "Discover this delightful double room located conveniently close to Uxbridge Town Centre, Brunel University, Heathrow, and the M4/M40 motorways.\n" +
                    "\n" +
                    "First-floor flat, ideal for a single occupant (£700) or two occupants (£800)—perfect for professionals or students.\n" +
                    "Monthly Rent: £800\n" +
                    "\n" +
                    "Postcode: UB8 2DQ\n" +
                    "\n" +
                    "Location Highlights:\n" +
                    "5-7 minutes' walk to Brunel University.\n" +
                    "10–12 minutes' walk to Uxbridge Centre and Tube Station.\n" +
                    "Easy access to West Drayton Station.\n" +
                    "\n" +
                    "\uD83D\uDD0D Additional Information:\n" +
                    "For immediate viewings, please get in touch via:\n" +
                    "\n" +
                    "WhatsApp using the reference: UB8 2DQ\n" +
                    "Don't miss out on this fantastic opportunity! \n" +
                    "Contact us now to arrange a viewing."

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
fun MainInfoSection(){

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
            text = "Agency",
            color = Color(33,33,33),
            fontSize = 18.sp
        )
    }
}

@Composable
fun DateInfoSection(){

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
                text = "ID: 1479943257",
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
    minShrinkHeight: Dp = 80.dp,
    toolbarOffset: Float,
    content: @Composable BoxScope.() -> Unit
) {
    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.9f)
    }

    val scrollDp = toolbarHeight + toolbarOffset.dp

    println("SCROLL DP == ${scrollDp}")
//    val animatedCardSize by animateDpAsState(
//        targetValue = if (scrollDp <= minShrinkHeight) minShrinkHeight else scrollDp,
//        animationSpec = tween(300, easing = LinearOutSlowInEasing)
//    )
    val animatedElevation by animateDpAsState(
        targetValue = if (scrollDp < minShrinkHeight + 20.dp) 10.dp else 0.dp,
        animationSpec = tween(500, easing = LinearOutSlowInEasing)
    )
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

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value
        )

    Box(
        modifier = animatedModifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = animatedElevation,
                shape = shape
            )
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
