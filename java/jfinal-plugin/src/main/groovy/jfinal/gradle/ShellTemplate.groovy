package jfinal.gradle

import groovy.text.SimpleTemplateEngine

class ShellTemplate {

    static SimpleTemplateEngine engine = new SimpleTemplateEngine()

    static def bat = '''
@ECHO OFF
SET UCP=.;
FOR /F %%F IN ('dir /s /b "%cd%\\\\libs\\\\*.jar"') DO call :set_cp %%F
goto exec_run
:set_cp
SET UCP=%UCP%;%1
goto :eof
:exec_run
java -cp %CLASSPATH%;%UCP% ${mainClass} %*
@ECHO ON
'''

    static def bash = '''
#!/usr/bin/env bash
base=\$(cd "\$(dirname \$0)"; pwd)
CP='.'
for file in `find \$base -name "*.jar"`;do
    CP=\$CP:\$file
done
java -cp \$CP ${mainClass} \$*
'''
}
