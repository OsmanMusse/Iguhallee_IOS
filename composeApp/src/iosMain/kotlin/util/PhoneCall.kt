package util

import io.ktor.http.Url
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun MakePhoneCall(number: String){
    val phoneNumber = "+44739369125"
    val numberUrl = NSURL(string = "tel://${phoneNumber}")
    UIApplication.sharedApplication.openURL(numberUrl)
}