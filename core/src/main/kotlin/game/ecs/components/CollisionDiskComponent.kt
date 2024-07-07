package game.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class CollisionDiskComponent : Component, Pool.Poolable {
    var collisionRadius = 1.0f;

    override fun reset() {
        collisionRadius = 1.0f;
    }

    companion object {
        val mapper = mapperFor<CollisionDiskComponent>()
    }
}
