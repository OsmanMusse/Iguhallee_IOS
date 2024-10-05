import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController(backDispatcher: BackDispatcherKt.BackDispatcher())
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}


struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea() // Compose has own keyboard handler
            .refreshable {}
    }
}





