package jfinal.gradle

import groovy.text.SimpleTemplateEngine
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.distribution.plugins.DistributionPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.bundling.Jar

class JFinalPlugin implements Plugin<Project> {

    static final String JF_CONF = "jfinalConf"

    static final String FAT_JAR_TASK ='fatJar'
    static final String BOOT_TASK ='boot'
    static final String DIST_TASK ='distApp'

    static SimpleTemplateEngine engine = new SimpleTemplateEngine()

    void apply(Project project) {
        project.pluginManager.apply(JavaPlugin)
        project.pluginManager.apply(DistributionPlugin)

        project.extensions.create(JF_CONF, JFinalPluginExtension)

        project.tasks.create(FAT_JAR_TASK, Jar) << {
            manifest.attributes = ['Implementation-Title'  : 'Gradle Jar File Example',
                                   'Implementation-Version': version,
                                   'Main-Class'            : "${project.jfinalConf.mainClass}"]

            baseName = project.name + '-assembly'
            from { configurations.compile.collect { it.isDirectory() ? it : zipTree(it) } }
            with Jar
        }

        project.task(BOOT_TASK).dependsOn('classes') << {
            project.javaexec {
                description = '运行指定main函数的java'
                classpath = project.convention.getPlugin(JavaPluginConvention).sourceSets.main.runtimeClasspath
                main = "${project.jfinalConf.mainClass}"
            }
        }

        String batText = this.class.getClassLoader().getResource("template/bin/run.bat.ftl").text
        String bashText = this.class.getClassLoader().getResource("template/bin/run.sh.ftl").text

        project.task(DIST_TASK).dependsOn('build') << {
            File dist = new File(project.buildDir, 'dist')
            def binding = [
                    mainClass: "${project.jfinalConf.mainClass}"
            ]
            project.copy {
                from project.configurations.compile
                into dist.absolutePath + "/lib"
            }

            project.copy {
                from project.buildDir.absolutePath + "/libs/" + "${project.jar.archiveName}"
                into dist.absolutePath + "/lib"
            }

            File bin = new File(dist, "bin")
            if (!bin.exists()) bin.mkdirs()
            new File(bin, 'run.bat').withWriter('utf-8') {
                it.write(engine.createTemplate(batText).make(binding).toString())
                it.flush()
            }
            new File(bin, 'run').withWriter('utf-8') {
                it.write(engine.createTemplate(bashText).make(binding).toString())
                it.flush()
            }
            new File(bin, 'run').setExecutable(true, false)
        }
    }
}
