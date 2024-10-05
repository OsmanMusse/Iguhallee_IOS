package screens.details.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramaas.iguhallee.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun CollapsingToolbar(
    onBackButtonPressed: () -> Unit,
    shape: Shape = RoundedCornerShape(
        bottomEnd = 0.dp,
        bottomStart = 0.dp
    ),
    collapsedBackgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    toolbarHeight: Dp,
    minShrinkHeight: Dp = 100.dp,
    toolbarOffset: Float,
) {
    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.9f)
    }

    val scrollDp = toolbarHeight + toolbarOffset.dp

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
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {  }
            .background(
                color = animatedColor,
                shape = shape
            )
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .clickable(interactionSource = MutableInteractionSource(), indication = null){},
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(MR.images.back_icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .rotate(180F)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onBackButtonPressed() }
                    )
            )
        }
    }
}