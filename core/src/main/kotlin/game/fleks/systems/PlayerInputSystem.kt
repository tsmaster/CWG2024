package game.fleks.systems

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import game.fleks.components.PlayerComponent
import game.fleks.components.TargetedSteeringComponent
import game.fleks.components.TransformComponent
import game.fleks.components.WeaponComponent

class PlayerInputSystem(
    private val viewport: Viewport = inject("world-viewport")
) : IteratingSystem (
    family = family { all (
        PlayerComponent,
        TransformComponent,
        TargetedSteeringComponent,
        WeaponComponent)}
) {
    override fun onTickEntity(entity: Entity) {
        val transformComponent = entity[TransformComponent]
        val steeringComponent = entity[TargetedSteeringComponent]
        val weaponComponent = entity[WeaponComponent]

        val tmpVec = Vector2(
            Gdx.input.x.toFloat(),
            Gdx.input.y.toFloat()
        )

        val worldPosOfCursor = viewport.unproject(tmpVec)

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
        {
            steeringComponent.hasTarget = true
            steeringComponent.targetWorldPos.x = worldPosOfCursor.x
            steeringComponent.targetWorldPos.y = worldPosOfCursor.y
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            weaponComponent.fireAt(world,
                Vector2(
                    transformComponent.position.x,
                    transformComponent.position.y), worldPosOfCursor)
        }
    }
}