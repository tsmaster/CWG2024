package game.fleks.components

import com.badlogic.gdx.graphics.g2d.Sprite
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.badlogic.gdx.math.Vector2
import com.github.quillraven.fleks.World
import game.screen.CWG2dGameScreenFleks

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

class WeaponComponent : Component<WeaponComponent> {
    override fun type() = WeaponComponent

    public var weaponType:WeaponType = game.fleks.components.WeaponType.MachineGun;
    public var weaponTeamIndex: Int = -1
    public var timeSinceLastShot: Float = 0f
    public var timeToReload: Float = 0.0f

    // TODO this does not belong in the component, put it in a system?
    fun fireAt(world: World, startPos: Vector2, targetPos: Vector2) {
        if (timeSinceLastShot < timeToReload)
        {
            return
        }
        //LOG.debug { "shooting" }
        timeSinceLastShot = 0.0f
        val shootVec = targetPos.sub(startPos).nor().scl(SHOOT_SPEED)

        world.entity {
            it += TransformComponent(startPos).apply {
                size.set(0.25f, 0.25f)
            }
            it += GraphicComponent(Sprite(CWG2dGameScreenFleks.bullet_texture)).apply {
                sprite.setSize(0.25f, 0.25f)
                sprite.setOriginCenter()
            }
            it += ProjectileComponent().apply {
                velocity.set(shootVec.x, shootVec.y)
                damagePayload = 60.0f
                lifetimeLeftSeconds = 1.0f
                teamIndex = weaponTeamIndex
            }
        }
    }

    companion object : ComponentType<WeaponComponent>()
}
