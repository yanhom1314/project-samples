@ECHO OFF
@rem ��ѯ����Ŀ¼�����п��ļ�
SET UCP=.;
FOR /F %%F IN ('dir /s /b "%cd%\libs\*.jar"') DO call :set_cp %%F
goto exec_run
@rem �����ļ�����CLASSPATH
:set_cp
SET UCP=%UCP%;%1
goto :eof
@rem ����
:exec_run
java -cp %CLASSPATH%;%UCP% demo.JettyBoot %*
@ECHO ON
