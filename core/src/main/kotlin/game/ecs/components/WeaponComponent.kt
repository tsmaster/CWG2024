package game.ecs.components

import com.artemis.ComponentManager
import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import game.CWGGame
import game.screen.CWG2dGameScreen
import ktx.ashley.entity
import ktx.ashley.mapperFor
import ktx.ashley.with
import ktx.log.logger

enum class WeaponType {
    MachineGun,
    Shotgun,
    Cannon,
    Flamethrower,
    Laser,
    Rocket,
    GuidedMissile,
    GuidedLaser,
    Lightning,
    IceRay
}

const val SHOOT_SPEED = 5.0f;

private val LOG = logger<WeaponComponent>()

class WeaponComponent: Component, Pool.Poolable {
    public var weaponType:WeaponType = game.ecs.components.WeaponType.MachineGun;
    public var weaponTeamIndex: Int = -1
    public var timeSinceLastShot: Float = 0f
    public var timeToReload: Float = 0.0f

    override fun reset() {
        weaponType = game.ecs.components.WeaponType.MachineGun;
        weaponTeamIndex = -1
        timeSinceLastShot = 0f
        timeToReload = 0.0f
    }

    fun fireAt(engine: Engine, startPos: Vector2, targetPos: Vector2) {
        if (timeSinceLastShot < timeToReload)
        {
            return
        }
        LOG.debug { "shooting" }
        timeSinceLastShot = 0.0f
        val shootVec = targetPos.sub(startPos).nor().scl(SHOOT_SPEED)

        engine.entity {
            with<TransformComponent> {
                setInitialPosition(startPos.x, startPos.y, 0.0f)
                size.set(0.25f, 0.25f)
            }
            with<GraphicComponent> {
                sprite.run {
                    setRegion(CWG2dGameScreen.bullet_texture)
                    setSize(0.25f, 0.25f)
                    setOriginCenter()
                }
            }
            with<ProjectileComponent> {
                velocity.set(shootVec.x, shootVec.y)
                damagePayload = 60.0f
                lifetimeLeftSeconds = 1.0f
                teamIndex = weaponTeamIndex
            }
        }
    }

    companion object {
        val mapper = mapperFor<WeaponComponent>()
    }
}
