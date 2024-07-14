package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

// TODO: update to Fleks

/* old ashley impl
class CollisionDiskComponent : Component, Pool.Poolable {
    var collisionRadius = 1.0f;

    override fun reset() {
        collisionRadius = 1.0f;
    }

    companion object {
        val mapper = mapperFor<CollisionDiskComponent>()
    }
}
*/

class CollisionDiskComponent : Component<CollisionDiskComponent> {
    override fun type() = CollisionDiskComponent

    companion object : ComponentType<CollisionDiskComponent>()
}