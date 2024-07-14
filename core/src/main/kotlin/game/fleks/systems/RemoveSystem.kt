package game.fleks.systems

import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.IteratingSystem

class RemoveSystem (
) : IteratingSystem (
    family = family { all(game.fleks.components.RemoveComponent)}
) {
    override fun onTickEntity(entity: com.github.quillraven.fleks.Entity) {
        entity[game.fleks.components.RemoveComponent].delay -= deltaTime

        if (entity[game.fleks.components.RemoveComponent].delay <= 0) {
            // TODO remove entity from world
            entity.remove()
        }
    }
}