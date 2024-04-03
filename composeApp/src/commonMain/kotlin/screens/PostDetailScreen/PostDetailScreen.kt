package screens.PostDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.alexzhirkevich.cupertino.CupertinoButton
import navigation.PostDetailScreenComponent
import navigation.RootComponent

@Composable
fun PostDetailScreen(rootComponent: PostDetailScreenComponent){
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CupertinoButton(
            onClick = { rootComponent.goBack() }
        ){
            Text("Go Back",color = Color.White)
        }
    }
}