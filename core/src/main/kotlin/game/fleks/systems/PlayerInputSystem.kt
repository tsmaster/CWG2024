package game.fleks.systems

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import game.fleks.components.PlayerComponent
import game.fleks.components.TargetedSteeringComponent
import game.fleks.components.FLTransformComponent
import game.fleks.components.WeaponComponent

/*
private val LOG = logger<PlayerInputSystem>()

// TODO: update to Fleks

class PlayerInputSystem(
    private val gameViewport: Viewport
) : IteratingSystem(
    allOf(
        PlayerComponent::class,
        TransformComponent::class,
        TargetedSteeringComponent::class,
        WeaponComponent::class
        ).get()) {
    private val tmpVec = Vector2()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformComponent = entity[TransformComponent.mapper]
        require(transformComponent != null) { "Entity |entity| must have TransformComponent. entity=$entity" }
        val steeringComponent = entity[TargetedSteeringComponent.mapper]
        require(steeringComponent != null) { "Entity |entity| must have SteeringComponent. entity=$entity" }
        val weaponComponent = entity[WeaponComponent.mapper]
        require(weaponComponent != null) { "Entity |entity| must have WeaponComponent. entity=$entity" }

        tmpVec.x = Gdx.input.x.toFloat()
        tmpVec.y = Gdx.input.y.toFloat()

        val worldPosOfCursor = gameViewport.unproject(tmpVec)
        //val diffX = tmpVec.x - transformComponent.position.x - transformComponent.size.x * 0.5f

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
        {
            //LOG.debug { "got click" }

            steeringComponent.hasTarget = true
            steeringComponent.targetWorldPos.x = worldPosOfCursor.x
            steeringComponent.targetWorldPos.y = worldPosOfCursor.y
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            //LOG.debug { "space for shoot pressed"}
            weaponComponent.fireAt(engine,
                Vector2(
                    transformComponent.position.x,
                    transformComponent.position.y), worldPosOfCursor)
        }
    }
}
*/

class PlayerInputSystem() : IteratingSystem (
    family = family { all (
        PlayerComponent,
        FLTransformComponent,
        TargetedSteeringComponent,
        WeaponComponent)}
) {
    override fun onTickEntity(entity: Entity) {
        TODO("Not yet implemented")
    }
}