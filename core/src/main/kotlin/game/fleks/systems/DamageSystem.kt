package game.fleks.systems

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import game.fleks.components.DamageableComponent
import game.fleks.components.FLTransformComponent


// TODO: do I need this?

/*
class DamageSystem : IteratingSystem(
    allOf(
    DamageableComponent::class,
    TransformComponent::class).exclude(RemoveComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "Entity |entity| must have TransformComponent. entity=$entity" }
        val damage = entity[DamageableComponent.mapper]
        require(damage != null) { "Entity |entity| must have DamageableComponent. entity=$entity" }

        // todo figure out damage vs projectiles

    }
}
*/

class DamageSystem : IteratingSystem(
    family = family { all(DamageableComponent, FLTransformComponent)}
) {
    override fun onTickEntity(entity: Entity) {
        TODO("Not yet implemented")
    }

}