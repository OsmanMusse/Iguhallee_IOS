package screens.details.util

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import util.Constants

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = Constants.DEFAULT_MINIMUM_TEXT_LINE,
    textAlign: TextAlign? = null
){
    var isExpanded by remember { mutableStateOf(false) }
    var isClickable by remember {( mutableStateOf(false))}
    var lastCharIndex by remember { mutableStateOf(0) }
    var textMaxLine by remember { mutableStateOf(0) }

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