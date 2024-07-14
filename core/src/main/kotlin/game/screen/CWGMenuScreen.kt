package game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
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

    private var buttonBBoxes = Array<Pair<Vector2, Vector2>>()

    override fun show() {
        LOG.debug{ "showing CWGMenuScreen" }

        bgsprite.setPosition(0.0f, 0.0f)

        btn1sprite.setPosition(8.0f, 8.0f)
        btn2sprite.setPosition(8.0f, 7.0f)
        btn3sprite.setPosition(8.0f, 6.0f)
        buttonBBoxes.run {
            add(Pair(Vector2(8.0f, 8.0f), Vector2(2.0f, 0.5f)))
            add(Pair(Vector2(8.0f, 7.0f), Vector2(2.0f, 0.5f)))
            add(Pair(Vector2(8.0f, 6.0f), Vector2(2.0f, 0.5f)))
        }

        LOG.info { "enumerating detected game controllers" }
        for (controller: Controller in Controllers.getControllers()) {
            LOG.info{ " found controller: " + controller.name }
        }

        val curController:Controller = Controllers.getCurrent()
        if (curController != null) {
            LOG.info { "current controller is: " + curController.name }
        }
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
            dispatchNumeric(0)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            dispatchNumeric(1)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            // exit game
            dispatchNumeric(2)
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            val clickPos: Vector2 = Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            val clickPosUnp = viewport.unproject(clickPos)
            LOG.info {
                "click at " + clickPos + clickPosUnp
            }

            val buttonIndex = findButton(clickPosUnp)
            LOG.info {
                "button index " + buttonIndex
            }

            dispatchNumeric(buttonIndex)
        }
    }

    private fun dispatchNumeric(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> game.setScreen<CWG2dGameScreenAshley>()
            1 -> game.setScreen<CWG2dGameScreenFleks>()
            2 -> game.setExit()
            else -> {}
        }
    }

    private fun findButton(clickPosUnp: Vector2): Int {
        for (i: Int in 0 ..< buttonBBoxes.size) {
            val buttonBox = buttonBBoxes[i]
            val lleft = buttonBox.first
            val sz = buttonBox.second
            if ((clickPosUnp.x >= lleft.x) &&
                (clickPosUnp.x <= lleft.x + sz.x) &&
                (clickPosUnp.y >= lleft.y) &&
                (clickPosUnp.y <= lleft.y + sz.y)) {
                return i
            }
        }
        return -1
    }

    override fun dispose() {
        bgtexture.dispose()

        btn1texture.dispose()
        btn2texture.dispose()
        btn3texture.dispose()

        super.dispose()
    }
}
