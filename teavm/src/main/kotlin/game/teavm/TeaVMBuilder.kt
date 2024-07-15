package game.teavm

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import com.github.xpenatan.gdx.backends.teavm.gen.SkipClass
import java.io.File

/** Builds the TeaVM/HTML application. */
@SkipClass
object TeaVMBuilder {
    @JvmStatic fun main(arguments: Array<String>) {
        val teaBuildConfiguration = TeaBuildConfiguration().apply {
            assetsPath.run {
                add(AssetFileHandle("../assets"))
            }
            webappPath = File("build/dist").canonicalPath
            // Register any extra classpath assets here:
            // additionalAssetsClasspathFiles += "ktx/demo/asset.extension"
            htmlTitle = "CWG 2024 on TeaVM"
        }

        // Register any classes or packages that require reflection here:
        // TeaReflectionSupplier.addReflectionClass("ktx.demo.reflect")

        val tool = TeaBuilder.config(teaBuildConfiguration)
        tool.mainClass = "game.teavm.TeaVMLauncher"
        TeaBuilder.build(tool)
    }
}