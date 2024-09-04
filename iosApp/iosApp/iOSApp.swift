import SwiftUI
import Firebase
import ComposeApp

@main
struct iOSApp: App {
    init() {
        FirebaseApp.configure()
        DependencyInjectionKt.doInitIOS()
    }
 
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}


class AppDelegate:NSObject, UIApplicationDelegate {
    var backDispatcher: BackDispatcher = BackDispatcherKt.BackDispatcher()
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        startKoin()
        return true
    }
}
