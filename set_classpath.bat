@echo OFF
@setlocal enableextensions enabledelayedexpansion
echo FolderPath=%cd%\lib
set JLIBS=.


@ for %%i in (lib\*.jar) do (
@     set JLIBS=!JLIBS!;%%i
)

@set CLASSPATH=%CLASSPATH%;%JLIBS%;
echo the CLASSPATH has set in this Terminal: %CLASSPATH%