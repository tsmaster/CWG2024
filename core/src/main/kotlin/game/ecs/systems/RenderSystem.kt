package game.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import game.ecs.components.GraphicComponent
import game.ecs.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.logger

private val LOG: ktx.log.Logger = logger<RenderSystem>()

class RenderSystem(
    private val batch: Batch,
    private val gameViewport: Viewport
) : SortedIteratingSystem (
    allOf(TransformComponent::class, GraphicComponent::class).get(),
    compareBy { entity -> entity[TransformComponent.mapper] }
)
{
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
    }
}
