@ECHO OFF
SET UCP=.;
FOR /F %%F IN ('dir /s /b "%cd%\\libs\\*.jar"') DO call :set_cp %%F
goto exec_run
:set_cp
SET UCP=%UCP%;%1
goto :eof
:exec_run
java -cp %CLASSPATH%;%UCP% ${mainClass} %*
@ECHO ON