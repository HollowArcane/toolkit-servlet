@REM SETTING VARIABLES
set "src-dir=src"
set "web-dir=web"
set "lib-dir=lib"
set "xml-dir=xml"

set "target-name=%1"
if "%1" == "" (
    set "target-name=test"
)

set "target-dir=C:/Users/tendr/Documents/Personnel/web/tomcat/apache-tomcat-9.0.85/webapps/"

@REM REMOVE EXISTING PROJECT FILES
rmdir /q/s "temp"
rmdir /q/s "temp-src"
del "%target-dir%/%target-name%.war"
rmdir /q/s "%target-dir%/%target-name%"

@REM CREATE A BASE PROJECT STRUCTURE
mkdir "temp/WEB-INF/classes"
mkdir "temp/WEB-INF/lib"
mkdir "temp-src"

@REM COPY UTILITY FILES INTO THE PROJECT
echo D | xcopy /q/s/y "%web-dir%" "temp"
echo D | xcopy /q/s/y "%lib-dir%" "temp/WEB-INF/lib"
echo D | xcopy /q/y "%xml-dir%" "temp/WEB-INF"

@REM COPY SOURCE FILE TO temp-src
echo D | xcopy /q/s/y "%src-dir%/controller" "temp-src"
echo D | xcopy /q/s/y "%src-dir%/model" "temp-src"

@REM  COMPITLE JAVA CODE
javac -parameters -d "temp/WEB-INF/classes" -cp "%lib-dir%/*" "temp-src/*.java"

@REM ZIP PROJECT INTO .war FILE
jar -cvf "%target-name%.war" -C "temp/" .

@REM COPY WAR FILE INTO webapps
echo D | xcopy /q/y "%target-name%.war" "%target-dir%"

@REM DELETE TEMPORARY FILES
del "%target-name%.war"
rmdir /q/s "temp"
rmdir /q/s "temp-src"

@REM RUN TOMCAT AND OPEN BROWSER
C:\Users\tendr\AppData\Local\Programs\Opera\launcher.exe --ran-launcher --remote http://localhost:8080/%target-name%/
start %target-dir%/../bin/startup.bat

pause
cls
