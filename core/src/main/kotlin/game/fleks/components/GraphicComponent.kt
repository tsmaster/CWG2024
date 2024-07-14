package game.fleks.components

import com.badlogic.gdx.graphics.g2d.Sprite
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

// TODO: looks like this is a dup of SpriteComponent, don't need both

class GraphicComponent(
    val sprite: Sprite
) : Component<GraphicComponent> {
    override fun type() = GraphicComponent



    fun reset() {
        sprite.texture = null
        sprite.setColor(1f, 1f, 1f, 1f)
    }

    companion object : ComponentType<GraphicComponent>()
}
