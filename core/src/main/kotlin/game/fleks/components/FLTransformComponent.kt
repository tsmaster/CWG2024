package game.fleks.components

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import game.screen.CWG2dGameScreenFleks
import ktx.log.logger

// TODO: extend for full functionality

data class FLTransformComponent(
    val xy: Vector2
) : Component<FLTransformComponent> {
    override fun type() = FLTransformComponent

    var position = Vector3(xy.x, xy.y, 0f)
    var previousPosition = Vector3(xy.x, xy.y, 0f)
    var interpolatedPosition = Vector3(xy.x, xy.y, 0f)
    var size = Vector2(1.0f, 1.0f)
    var rotationDeg = 0.0f

    fun setInitialPosition(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
        previousPosition.set(x, y, z)
        interpolatedPosition.set(x, y, z)
    }

    companion object : ComponentType<FLTransformComponent>() {
        val log = logger<CWG2dGameScreenFleks>()
    }
}

