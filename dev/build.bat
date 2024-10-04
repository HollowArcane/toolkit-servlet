@REM SETTING VARIABLES
set "src-dir=src"
set "lib-dir=../test/lib"

set "target-name=%1"
if "%1" == "" (
    set "target-name=toolkit-servlet"
)

set "target-dir=../test/lib"

@REM REMOVE EXISTING PROJECT FILES
rmdir /q/s "temp"
echo "%target-dir%"
del "%target-dir%/%target-name%.jar"

@REM CREATE A BASE PROJECT STRUCTURE
mkdir "temp/bin"
mkdir "temp/src"

@REM COPY UTILITY FILES INTO THE PROJECT
echo D | xcopy /q/s/y "%src-dir%/toolkit/bean" "temp/src"
echo D | xcopy /q/s/y "%src-dir%/toolkit/http" "temp/src"
echo D | xcopy /q/s/y "%src-dir%/toolkit/jsp" "temp/src"
echo D | xcopy /q/s/y "%src-dir%/toolkit/util" "temp/src"
echo D | xcopy /q/s/y "%src-dir%/toolkit/exception" "temp/src"

@REM  COMPITLE JAVA CODE
javac -Xdiags:verbose --release 12 -d "temp/bin" -cp "%lib-dir%/*" temp/src/*.java

@REM ZIP PROJECT INTO .jar FILE
jar -cvf "temp/%target-name%.jar" -C "temp/bin" .
rmdir /q/s "temp/bin"
rmdir /q/s "temp/src"

@REM COPY JAR FILE INTO test
echo D | xcopy /q/s/y "temp" "%target-dir%"

@REM DELETE TEMPORARY FILES
rmdir /q/s "temp"
pause
cls