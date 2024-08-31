@REM SETTING VARIABLES
set "web-dir=web"

set "target-name=%1"
if "%1" == "" (
    set "target-name=test"
)

set "target-dir=C:/Users/tendr/Documents/Personnel/web/tomcat/apache-tomcat-9.0.85/webapps/"


echo D | xcopy /q/s/y "%web-dir%" "%target-dir%/%target-name%"

pause
cls