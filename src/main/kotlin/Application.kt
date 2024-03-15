import view.HomeScreen
import view.ScreenStack

fun main() {
    val homeScreen = HomeScreen()

    homeScreen.start()
    while (ScreenStack.isEmpty().not()) {
        ScreenStack.peek()?.waitInput()
        ScreenStack.peek()?.loadMenu()
    }
}