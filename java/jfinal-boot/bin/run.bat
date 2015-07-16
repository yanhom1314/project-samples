@ECHO OFF
@rem 轮询处理目录下所有库文件
SET UCP=.;
FOR /F %%F IN ('dir /s /b "%cd%\libs\*.jar"') DO call :set_cp %%F
goto exec_run
@rem 将库文件加入CLASSPATH
:set_cp
SET UCP=%UCP%;%1
goto :eof
@rem 运行
:exec_run
java -cp %CLASSPATH%;%UCP% demo.JettyBoot %*
@ECHO ON
