package game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import game.CWGGame
import ktx.log.logger

private val LOG = logger<CWG3dGameScreen>()

class CWG3dGameScreen(game: CWGGame) : CWGScreen(game) {
    override fun show() {
        LOG.debug{ "showing CWGTitleScreen" }
    }

    override fun render(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.setScreen<BDGSplashScreen>()
        }
    }
}
