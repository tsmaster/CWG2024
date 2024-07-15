package game.fleks.systems

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import game.fleks.components.WeaponComponent

class WeaponCooldownSystem () :IteratingSystem (
    family = family { all (
        WeaponComponent
    )}
) {
    override fun onTickEntity(entity: Entity) {
        entity[WeaponComponent].timeSinceLastShot += deltaTime
    }
}