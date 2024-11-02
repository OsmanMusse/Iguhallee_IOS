package screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import decompose.tab.account.AccountComponent


@Composable
fun AccountListView(component: AccountComponent){

    println("Account Component Hashcode == ${component.hashCode()}")
    val model by component.model.subscribeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ){
        Text(modifier = Modifier.align(Alignment.Center), text = model.text)

        Button(
            modifier = Modifier.align(Alignment.Center).padding(top = 60.dp),
            onClick = {
                component.updateText("Update btn was clicked")
            }
        ){
            Text(text = "Update text")
        }
    }


}