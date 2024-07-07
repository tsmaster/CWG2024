package lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import game.CWGGame

fun main() {
    //if (StartupHelper.startNewJvmIfRequired()) return  // This handles macOS support and helps on Windows.

    Lwjgl3Application(CWGGame(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("Cars With Guns 2024 libKTX build")
        useVsync(true)
        setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate)
        setWindowedMode(1280, 764)
        setWindowIcon("icon_cwg_128.png", "icon_cwg_64.png", "icon_cwg_32.png", "icon_cwg_16.png")
    })
}

