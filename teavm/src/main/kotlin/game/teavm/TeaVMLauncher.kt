@file:JvmName("TeaVMLauncher")

package game.teavm

import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration
import com.github.xpenatan.gdx.backends.teavm.TeaApplication
import game.CWGGame

/** Launches the TeaVM/HTML application. */
fun main() {
    val config = TeaApplicationConfiguration("canvas").apply {
        width = 1280
        height = 768
    }
    TeaApplication(CWGGame(), config)
}