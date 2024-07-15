package game.fleks.systems

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import game.fleks.components.GraphicComponent
import game.fleks.components.FLTransformComponent
import ktx.log.logger

// TODO: update for full functionality

//private val LOG: ktx.log.Logger = logger<RenderSystem>()

class RenderSystem(
    private val viewport: Viewport = inject("world-viewport"),
    private val batch: SpriteBatch = inject("spritebatch")
) : IteratingSystem(

    family = family {
        all(FLTransformComponent, GraphicComponent)
    }
)
{
    /*
    override fun update(deltaTime: Float) {
        forceSort()
        gameViewport.apply()
        batch.use(gameViewport.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformComponent = entity[TransformComponent.mapper]
        require(transformComponent != null) { "Entity |entity| must have TransformComponent. entity=$entity" }

        val graphicComponent = entity[GraphicComponent.mapper]
        require(graphicComponent != null) { "Entity |entity| must have GraphicComponent. entity=$entity" }

        if (graphicComponent.sprite == null)
        {
            LOG.error { "Entity |entity| must have a texture for rendering. entity=$entity" }
            return
        }

        graphicComponent.sprite.run {
            rotation = transformComponent.rotationDeg
            setBounds(
                transformComponent.interpolatedPosition.x,
                transformComponent.interpolatedPosition.y,
                transformComponent.size.x,
                transformComponent.size.y)
            draw(batch)
        }

     */
    override fun onTick() {
        viewport.apply()
        batch.begin()
        super.onTick()
        batch.end()
    }

    override fun onTickEntity(entity: com.github.quillraven.fleks.Entity) {
        //entity[GraphicComponent].sprite.draw(batch)

        val transformComponent = entity[FLTransformComponent]
        val graphicComponent = entity[GraphicComponent]

        if (graphicComponent.sprite == null)
        {
            log.error { "Entity |entity| must have a texture for rendering. entity=$entity" }
            return
        }

        //log.info { "drawing sprite"}

        graphicComponent.sprite.run {
            rotation = transformComponent.rotationDeg
            setBounds(
                transformComponent.position.x,
                transformComponent.position.y,
                transformComponent.size.x,
                transformComponent.size.y)
            draw(batch)
        }
    }

    companion object {
        private val log = logger<RenderSystem>()
    }
}

