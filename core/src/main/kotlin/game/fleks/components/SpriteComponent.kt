package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

// TODO: extend to handle sprite color, etc

data class SpriteComponent(
    var path: String = "",
    var rotation: Float = 0f
) : Component<SpriteComponent> {
    override fun type(): ComponentType<SpriteComponent> = SpriteComponent

    companion object : ComponentType<SpriteComponent> ()
}
