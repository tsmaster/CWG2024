package game

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import game.screen.*
import ktx.app.KtxGame
import ktx.log.logger

private val LOG = logger<CWGGame>()

class CWGGame : KtxGame<CWGScreen>() {
    val gameViewport = FitViewport(16f, 9f);
    val uiViewport = FitViewport(1280f, 768f);
    val batch: Batch by lazy { SpriteBatch(300) }

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        LOG.debug { "welcome to CWG instance" }
        addScreen(BDGSplashScreen(this))
        addScreen(CWGTitleScreen(this))
        addScreen(CWGMenuScreen(this))

        addScreen(CWG2dGameScreenFleks(this))
        addScreen(CWG3dGameScreen(this))

        setScreen<BDGSplashScreen>()
    }

    override fun dispose() {
        batch.dispose()
    }

    fun setExit() {
        Gdx.app.exit()
    }
}
