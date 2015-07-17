package jfinal.gradle

import groovy.text.SimpleTemplateEngine
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention

class JFinalPlugin implements Plugin<Project> {
    static SimpleTemplateEngine engine = new SimpleTemplateEngine()

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
            File dist = new File(project.buildDir, 'dist')
            def binding = [
                    mainClass: "${project.jfinalConf.mainClass}"
            ]
            project.copy {
                from project.configurations.compile
                into dist.absolutePath + "/libs"
            }

            project.copy {
                from project.buildDir.absolutePath + "/libs/" + "${project.jar.archiveName}"
                into dist.absolutePath + "/libs"
            }
            new File(dist, 'run.bat').withWriter('utf-8') {
                it.write(engine.createTemplate(batText).make(binding).toString())
                it.flush()
            }
            new File(dist, 'run').withWriter('utf-8') {
                it.write(engine.createTemplate(bashText).make(binding).toString())
                it.flush()
            }
        }
    }
}
