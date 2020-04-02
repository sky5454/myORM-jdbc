@ECHO OFF
title checksum...

@ECHO ON
cd %~dp0\..
certutil -hashfile mysql-connector-java-8.0.17.jar          SHA1

cd %~dp0\..\multPlatform
certutil -hashfile swt-4.15-win32-win32-x86_64.zip       SHA512
certutil -hashfile swt-4.15-gtk-linux-x86_64.zip             SHA512
certutil -hashfile swt-4.15-gtk-linux-ppc64le.zip           SHA512
certutil -hashfile swt-4.15-cocoa-macosx-x86_64.zip    SHA512
 

notepad %~dp0\check.sum
pause