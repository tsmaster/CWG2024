package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.badlogic.gdx.math.Vector2
import ktx.log.logger

// TODO: update to Fleks

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

/*
private val LOG = logger<WeaponComponent>()

class WeaponComponent: Component, Pool.Poolable {
    public var weaponType:WeaponType = game.fleks.components.WeaponType.MachineGun;
    public var weaponTeamIndex: Int = -1
    public var timeSinceLastShot: Float = 0f
    public var timeToReload: Float = 0.0f

    override fun reset() {
        weaponType = game.fleks.components.WeaponType.MachineGun;
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

        /*
        engine.entity {
            with<TransformComponent> {
                setInitialPosition(startPos.x, startPos.y, 0.0f)
                size.set(0.25f, 0.25f)
            }
            with<GraphicComponent> {
                sprite.run {
                    setRegion(CWG2dGameScreenAshley.bullet_texture)
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
        }*/
    }

    companion object {
        val mapper = mapperFor<WeaponComponent>()
    }
}*/


class WeaponComponent : Component<WeaponComponent> {
    var timeSinceLastShot: Float = 0.0f

    override fun type() = WeaponComponent

    companion object : ComponentType<WeaponComponent>()
}
