@echo off
color 03

pushd "%~dp0"
rundll32 setupapi.dll,InstallHinfSection DefaultUnInstall 128 .\install.inf
popd

echo.
echo ~_~  Taobao Compressor 卸载成功  ~_~
echo.
pause
