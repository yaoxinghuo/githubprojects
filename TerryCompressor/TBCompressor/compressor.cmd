@echo off
color 03
REM =====================================
REM    Taobao Compressor CMD Script
REM
REM     - by yubo@taobao.com
REM     - 2009-02-12
REM =====================================
SETLOCAL ENABLEEXTENSIONS

echo.
echo Taobao Compressor v2.4.2

REM 过滤文件后缀，只压缩js和css
if "%~x1" NEQ ".js" (
    if "%~x1" NEQ ".css" (
        echo.
        echo **** 请选择CSS或JS文件
        echo.
        goto End
    )
)

REM 检查Java环境
if "%JAVA_HOME%" == "" goto NoJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto NoJavaHome
if not exist "%JAVA_HOME%\bin\native2ascii.exe" goto NoJavaHome

REM 获取压缩后的文件名，规则为：
REM 1. 文件名有.source时: filename.source.js -> filename.js
REM 2. 其它情况：filename.js -> filename-min.js
set RESULT_FILE=%~n1-min%~x1
dir /b "%~f1" | find ".source." > nul
if %ERRORLEVEL% == 0 (
    for %%a in ("%~n1") do (
        set RESULT_FILE=%%~na%~x1
    )
)

REM 调用yuicompressor压缩文件
"%JAVA_HOME%\bin\java.exe" -jar "%~dp0yuicompressor.jar" --charset GB18030 "%~nx1" -o "%RESULT_FILE%"

REM 下面解决编码问题：当js文件的编码与页面编码不一致时，非ascii字符会导致乱码，处理办法是：
REM 1. 对于js文件，还需要调用native2ascii.exe将非ascii字符用\uxxxx表示
REM 2. 对于css文件，需要将非ascii字符转换为\uxxxx, 同时将u去掉（css只认识\xxxx）
REM 3. 目前只处理了js文件
REM 4. 对于css文件，只有font-family和:after等中可能有非ascii字符，情况很少，手工处理
if "%~x1" == ".js" (
    copy /y "%RESULT_FILE%" "%RESULT_FILE%.swp" > nul
    "%JAVA_HOME%\bin\native2ascii.exe" -encoding GB18030 "%RESULT_FILE%.swp" "%RESULT_FILE%"
    del /q "%RESULT_FILE%.swp"
)

REM 显示压缩结果
if %ERRORLEVEL% == 0 (
    echo.
    echo 压缩文件 %~nx1 到 %RESULT_FILE%
    for %%a in ("%RESULT_FILE%") do (
        echo 文件大小从 %~z1 bytes 压缩到 %%~za bytes
    )
    echo.
) else (
    echo.
    echo **** 文件 %~nx1 中有写法错误，请仔细检查
    echo.
	goto End
)

REM 检查是否含有内网地址(taobao.net)
find /i "taobao.net" "%RESULT_FILE%" > nul
if %ERRORLEVEL% == 0 (
	echo.
	echo **** 注意：%~nx1 中含有 taobao.net, 建议修改为线上地址
	echo.
)
goto End


:NoJavaHome
echo.
echo **** 请先安装JDK并设置JAVA_HOME环境变量
echo.

:End
ENDLOCAL
pause
