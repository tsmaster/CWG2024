package game.fleks.systems

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import game.fleks.components.DamageableComponent
import game.fleks.components.ProjectileComponent
import game.fleks.components.TransformComponent
import game.fleks.components.RemoveComponent

class ProjectileSystem() : IteratingSystem (
    family = family { all(
        ProjectileComponent,
        TransformComponent
    )}
) {
    override fun onTickEntity(entity: Entity) {
        val transform = entity[TransformComponent]
        val projectile = entity[ProjectileComponent]

        projectile.lifetimeLeftSeconds -= deltaTime
        if (projectile.lifetimeLeftSeconds <= 0.0f)
        {
            entity.configure {
                it += RemoveComponent()
            }
            return
        }

        transform.position.x += projectile.velocity.x * deltaTime
        transform.position.y += projectile.velocity.y * deltaTime

        // check collision with damageable
        val damageableEntities = world.family {
            all(DamageableComponent,
                TransformComponent)
        }

        damageableEntities.forEach { dmgEntity ->
            val dmgTransform = dmgEntity[TransformComponent]
            val dmgDamage = dmgEntity[DamageableComponent]

            if (dmgDamage.dmgTeamIndex == projectile.teamIndex)
            {
                return@forEach
            }

            val dmg_x = dmgTransform.position.x
            val dmg_y = dmgTransform.position.y

            val delta_x = transform.position.x - dmg_x
            val delta_y = transform.position.y - dmg_y

            val COLLIDE_DIST = 1.0f
            val COLLIDE_DIST_SQR = COLLIDE_DIST * COLLIDE_DIST

            if (delta_x * delta_x + delta_y * delta_y <= COLLIDE_DIST_SQR) {
                // collision
                dmgDamage.curHealth -= projectile.damagePayload

                // remove projectile
                entity.configure {
                    it += RemoveComponent()
                }

                if (dmgDamage.curHealth <= 0.0f)
                {
                    // TODO make explosion
                    dmgEntity.configure {
                        it += RemoveComponent()
                    }
                }
            }
        }
    }
}