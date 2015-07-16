package demo

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("jfinal", GreetingPluginExtension)
        project.task('hello').dependsOn('classes') << {
            println "${project.jfinal.message} from ${project.jfinal.greeter}"
        }
    }
}
