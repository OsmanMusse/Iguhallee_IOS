
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import decompose.landing.LandingComponent
import decompose.root.RootComponent
import io.github.alexzhirkevich.cupertino.CupertinoDivider
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBarDefaults
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.MainScreen



@Composable
@Preview
fun App(rootComponent: RootComponent) {

    println("Child Created == 2")

    val childStack = rootComponent.routerState

    Children(
        stack = childStack,
        modifier = Modifier.fillMaxSize(),
        content = { child ->
            println("Child Created == ")
           when(val childCreated = child.instance){
               is RootComponent.RootChild.SplashRoot -> Unit
               is RootComponent.RootChild.MainRoot -> MainScreen(childCreated.mainComponent)
               is RootComponent.RootChild.LandingRoot -> {
                   OnBoardingSelectLocation(
                       component = childCreated.landingComponent,
                       onLocationSelect = { rootComponent.navigateToHomeScreen(it) }
                   )
               }
           }
        }
    )
}


@Composable
fun OnBoardingSelectLocation(
    onLocationSelect: (location: String) -> Unit,
    component: LandingComponent
){
    val mainColor =  Color(71, 134, 255)
    val scope = rememberCoroutineScope()

    CupertinoScaffold(
        topBar = {
            CupertinoTopAppBar(
                title = {
                    CupertinoText(
                        text = "Select Location",
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )
                },
                colors = CupertinoTopAppBarDefaults.topAppBarColors(containerColor = mainColor),
                isTranslucent = false
            )
        }
    ){
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            val listItem = arrayListOf("Garowe","Mogadishu","Hargeisa","Burco","Bosaso","Gaalkacyo")
            listItem.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER,{ it }))
            items(listItem){ location ->
                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(),
                            onClick = {
                                scope.launch {
                                    component.setUserLocation(location)
                                }
                                onLocationSelect(location)
                            }
                        )
                        .padding(start = 10.dp)
                        .height(60.dp),

                    ){
                    CupertinoText(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = location,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 17.sp
                    )


                    CupertinoDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                    )
                }

            }
        }
    }
}


