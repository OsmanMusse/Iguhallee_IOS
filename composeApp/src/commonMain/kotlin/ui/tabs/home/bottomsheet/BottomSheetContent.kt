package ui.tabs.home.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramaas.iguhallee.MR
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults


@Composable
fun CuperintoBottomSheetContent(padding: PaddingValues) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(MR.images.default_hero),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = padding.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp,
                    bottom = padding.calculateBottomPadding()
                )
                .align(Alignment.BottomStart),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Icon(
                painter = painterResource(MR.images.final_iguhallee_icon1),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color.Unspecified
            )
            Icon(
                modifier = Modifier.width(125.dp),
                painter = painterResource(MR.images.iguhallee_icon),
                contentDescription = null,
                tint = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Find what you need, wherever you are",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                minLines = 2
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(17.dp)
            ) {
                CupertinoButton(
                    modifier = Modifier.fillMaxWidth().size(48.dp),
                    colors = CupertinoButtonDefaults.plainButtonColors(
                        containerColor = Color(71, 134, 255)
                    ),
                    shape = RoundedCornerShape(6.dp),
                    onClick = {}
                ) {
                    Text(
                        text = "Sign up",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                CupertinoButton(
                    modifier = Modifier.fillMaxWidth().size(48.dp),
                    colors = CupertinoButtonDefaults.plainButtonColors(
                        containerColor = Color(71, 134, 255)
                    ),
                    shape = RoundedCornerShape(6.dp),
                    onClick = {}
                ) { Text(text = "Log in",fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}