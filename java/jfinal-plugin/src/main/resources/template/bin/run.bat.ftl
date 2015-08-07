@echo off
setlocal enabledelayedexpansion

for %%? in ("%~dp0..") do set APP_HOME=%%~f?

for %%a in ("%APP_HOME%\\lib\\*.jar") do set APP_CP=!APP_CP!%%a;

java -cp .;%APP_CP% ${mainClass} %*
@echo on