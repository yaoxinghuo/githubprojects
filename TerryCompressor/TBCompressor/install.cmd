@echo off
color 03

pushd "%~dp0"
rundll32 setupapi.dll,InstallHinfSection DefaultInstall 128 .\install.inf
popd

echo.
echo ~_~  Taobao Compressor 安装成功  ~_~
echo.
pause
