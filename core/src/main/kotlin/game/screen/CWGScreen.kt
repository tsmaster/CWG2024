package game.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import game.CWGGame
import ktx.app.KtxScreen

abstract class CWGScreen(
    val game:CWGGame,
    val batch: Batch = game.batch,
    val gameViewport: Viewport = game.gameViewport,
    val uiViewport: Viewport = game.uiViewport,
    val engine: Engine = game.engine,
) :KtxScreen {

    // TODO maybe add in an update, too?


    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        gameViewport.update(width, height, true);
    }

}
