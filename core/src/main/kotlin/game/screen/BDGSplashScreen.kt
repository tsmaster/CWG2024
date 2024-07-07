package game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.FitViewport
import game.CWGGame
import ktx.graphics.use
import ktx.log.logger

private val LOG = logger<BDGSplashScreen>()

class BDGSplashScreen(game: CWGGame) : CWGScreen(game) {
    private val viewport = FitViewport(16f, 9f);
    private val bgtexture = Texture(Gdx.files.internal("screens/bigdicegameslogo.png"))
    private val bgsprite = Sprite(bgtexture).apply {
        setSize(16.0f, 9.0f)
    }

    override fun show() {
        LOG.debug{ "showing BDGSplashScreen" }

        bgsprite.setPosition(0.0f, 0.0f)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        viewport.update(width, height, true);
    }

    override fun render(delta: Float) {
        viewport.apply()
        batch.use(viewport.camera.combined) {
            bgsprite.draw(it)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) ||
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen<CWGTitleScreen>()
        }
    }

    override fun dispose() {
        bgtexture.dispose()
        super.dispose()
    }
}
