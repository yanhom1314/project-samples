package demo

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("greeting", GreetingPluginExtension)
        project.task('hello') << {
            println "${project.greeting.message} from ${project.greeting.greeter}"
        }
    }
}
