package game.fleks.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import com.github.quillraven.fleks.ComponentType
import ktx.ashley.mapperFor

class RemoveComponent : com.github.quillraven.fleks.Component<RemoveComponent> {
    override fun type() = RemoveComponent

    var delay = 0.0f

    companion object: ComponentType<RemoveComponent>()
}
