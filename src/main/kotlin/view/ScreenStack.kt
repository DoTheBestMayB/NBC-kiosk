package view

object ScreenStack {

    private val screenStacks = mutableListOf<Screen>()

    fun push(screen: Screen) {
        if (peek() == screen) {
            return
        }
        screenStacks.add(screen)
    }

    fun pop() {
        screenStacks.removeLastOrNull()
    }

    fun peek(): Screen? {
        return screenStacks.lastOrNull()
    }

    fun isEmpty() = screenStacks.isEmpty()
}