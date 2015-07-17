JFinal Gradle Plugin
===

## สนำร`build.gradle`

		apply plugin: 'jfinal-plugin'
		
		buildscript {
        	repositories {
                mavenLocal()
                jcenter()
            }
            dependencies {
                classpath group: 'io.lyf', name: 'jfinal-plugin', version: '0.1'
            }
        }
        
        jfinalConf {        	
        	mainClass = "demo.JettyBoot"
        }
