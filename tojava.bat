@echo off

rmdir "C:\Program Files\Java\jdk"

mklink /D "C:\Program Files\Java\jdk" "C:\Program Files\Java\jdk%1"

@echo on