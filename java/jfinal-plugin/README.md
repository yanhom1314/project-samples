JFinal Gradle Plugin
===

## ʹ��`build.gradle`

		apply plugin: 'jfinal-plugin'
		
		buildscript {
        	repositories {
                mavenLocal()
            }
            dependencies {
                classpath group: 'io.lyf', name: 'jfinal-plugin', version: '0.1'
            }
        }
        
        jar {
            manifest {
                attributes('Main-Class': 'demo.JettyBoot')
            }
        }

+ ����: