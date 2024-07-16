package game.fleks.components

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class TransformComponent(
    val xy: Vector2
) : Component<TransformComponent> {
    override fun type() = TransformComponent

    var position = Vector3(xy.x, xy.y, 0f)
    var size = Vector2(1.0f, 1.0f)
    var rotationDeg = 0.0f

    companion object : ComponentType<TransformComponent>()
}

