package game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.FitViewport
import game.CWGGame
import ktx.graphics.use
import ktx.log.*


private val LOG = logger<CWGMenuScreen>()

class CWGMenuScreen(game: CWGGame) : CWGScreen(game) {
    private val viewport = FitViewport(16f, 9f);
    private val bgtexture = Texture(Gdx.files.internal("screens/mainmenu.png"))
    private val bgsprite = Sprite(bgtexture).apply {
        setSize(16.0f, 9.0f)
    }

    private val btn1texture = Texture(Gdx.files.internal("buttons/btn-2dgame.png"))
    private val btn1sprite = Sprite(btn1texture).apply {
        setSize(2.0f, 0.5f)
    }
    private val btn2texture = Texture(Gdx.files.internal("buttons/btn-3dgame.png"))
    private val btn2sprite = Sprite(btn2texture).apply {
        setSize(2.0f, 0.5f)
    }
    private val btn3texture = Texture(Gdx.files.internal("buttons/btn-exit.png"))
    private val btn3sprite = Sprite(btn3texture).apply {
        setSize(2.0f, 0.5f)
    }

    override fun show() {
        LOG.debug{ "showing CWGMenuScreen" }

        bgsprite.setPosition(0.0f, 0.0f)

        btn1sprite.setPosition(8.0f, 8.0f)
        btn2sprite.setPosition(8.0f, 7.0f)
        btn3sprite.setPosition(8.0f, 6.0f)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        viewport.update(width, height, true);
    }

    override fun render(delta: Float) {
        viewport.apply()

        batch.use(viewport.camera.combined) {
            bgsprite.draw(it)

            btn1sprite.draw(it)
            btn2sprite.draw(it)
            btn3sprite.draw(it)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.setScreen<CWG2dGameScreenAshley>()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            game.setScreen<CWG2dGameScreenFleks>()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            // exit game
            game.setExit()
        }
    }

    override fun dispose() {
        bgtexture.dispose()

        btn1texture.dispose()
        btn2texture.dispose()
        btn3texture.dispose()

        super.dispose()
    }
}
