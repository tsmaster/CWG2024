package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

class AIControlledComponent : Component<AIControlledComponent> {
    override fun type() = AIControlledComponent

    companion object : ComponentType<AIControlledComponent>()
}