package game.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

class AIControlledComponent : Component, Pool.Poolable {
    override fun reset() {
    }
}
