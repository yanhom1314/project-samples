package jfinal.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention

class JFinalPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("jfinalConf", JFinalPluginExtension)
        project.task('hello') << {
            println "Run ${project.jfinalConf.mainClass} ${project.jfinalConf.message} ${project.jfinalConf.greeter}."
        }

        project.task('boot').dependsOn('classes') << {
            project.javaexec {
                description = '运行指定main函数的java'
                classpath = project.convention.getPlugin(JavaPluginConvention).sourceSets.main.runtimeClasspath
                main = "${project.jfinalConf.mainClass}"
            }
        }

        String batText = this.class.getClassLoader().getResource("template/bin/run.bat").text
        String bashText = this.class.getClassLoader().getResource("template/bin/run").text

        project.task('packageBin').dependsOn('build') << {
            def binding = [
                    mainClass: "${project.jfinalConf.mainClass}"
            ]
            project.copy {
                from project.configurations.compile
                into project.buildDir.absolutePath + "/libs"
            }
            new File(project.buildDir, 'run.bat').withWriter('utf-8') {
                it.write(ShellTemplate.engine.createTemplate(batText).make(binding).toString())
                it.flush()
            }
            new File(project.buildDir, 'run').withWriter('utf-8') {
                it.write(ShellTemplate.engine.createTemplate(bashText).make(binding).toString())
                it.flush()
            }
        }
    }
}
