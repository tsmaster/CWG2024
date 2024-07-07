package game.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import game.ecs.components.DamageableComponent
import game.ecs.components.RemoveComponent
import game.ecs.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get

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
