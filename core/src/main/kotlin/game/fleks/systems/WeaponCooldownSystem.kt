package game.fleks.systems

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import game.fleks.components.WeaponComponent


// TODO: update to Fleks
/*
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
*/

class WeaponCooldownSystem () :IteratingSystem (
    family = family { all (
        WeaponComponent
    )}
) {
    override fun onTickEntity(entity: Entity) {
        entity[WeaponComponent].timeSinceLastShot += deltaTime
    }
}