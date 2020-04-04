@ECHO OFF
title checksum...

@ECHO ON
cd %~dp0\..
certutil -hashfile mysql-connector-java-8.0.17.jar          SHA1

notepad %~dp0\check.sum
pause