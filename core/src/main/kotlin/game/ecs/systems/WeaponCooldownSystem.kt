package game.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import game.ecs.components.WeaponComponent
import ktx.ashley.allOf
import ktx.ashley.get

class WeaponCooldownSystem : IteratingSystem(
    allOf (
        WeaponComponent::class
    ).get())
{
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val weapon = entity[WeaponComponent.mapper]
        require(weapon != null) { "entity must have weapon"}

        weapon.timeSinceLastShot += deltaTime
    }
}
