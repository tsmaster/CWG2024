package game.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.Family.all
import com.badlogic.ashley.systems.IteratingSystem
import game.CWGGame
import game.ecs.components.DamageableComponent
import game.ecs.components.ProjectileComponent
import game.ecs.components.RemoveComponent
import game.ecs.components.TransformComponent
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.logger

private val LOG = logger<ProjectileSystem>()

class ProjectileSystem : IteratingSystem(
    allOf(ProjectileComponent::class,
          TransformComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "Transform not set" }
        val projectile = entity[ProjectileComponent.mapper]
        require(projectile != null) { "Projectile not set" }

        //LOG.debug {"processing projectile"}

        projectile.lifetimeLeftSeconds -= deltaTime
        if (projectile.lifetimeLeftSeconds <= 0.0f)
        {
            //LOG.debug{"timing out projectile"}
            entity.addComponent<RemoveComponent>(engine)
            return
        }
        //LOG.debug{"moving projectile ${projectile.velocity}"}
        transform.interpolatedPosition.x += projectile.velocity.x * deltaTime
        transform.interpolatedPosition.y += projectile.velocity.y * deltaTime

        // check collision with damageable
        val damageableEntities = engine.getEntitiesFor(
            allOf(DamageableComponent::class,
                TransformComponent::class).get()
        )

        for (dmgEntity in damageableEntities) {
            val dmgTransform = dmgEntity[TransformComponent.mapper]
            require(dmgTransform != null) { "Transform not set" }
            val dmgDamage = dmgEntity[DamageableComponent.mapper]
            require(dmgDamage != null) { "Transform not set" }

            if (dmgDamage.dmgTeamIndex == projectile.teamIndex)
            {
                continue
            }

            val dmg_x = dmgTransform.interpolatedPosition.x
            val dmg_y = dmgTransform.interpolatedPosition.y

            val delta_x = transform.interpolatedPosition.x - dmg_x
            val delta_y = transform.interpolatedPosition.y - dmg_y

            val COLLIDE_DIST = 1.0f
            val COLLIDE_DIST_SQR = COLLIDE_DIST * COLLIDE_DIST

            if (delta_x * delta_x + delta_y * delta_y <= COLLIDE_DIST_SQR) {
                // collision
                dmgDamage.curHealth -= projectile.damagePayload

                // remove projectile
                entity.addComponent<RemoveComponent>(engine)

                if (dmgDamage.curHealth <= 0.0f)
                {
                    // TODO make explosion
                    dmgEntity.addComponent<RemoveComponent>(engine)
                }
            }
        }
    }
}
