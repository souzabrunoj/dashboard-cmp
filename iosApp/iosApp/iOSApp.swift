import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init(){
        HelperDiKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
