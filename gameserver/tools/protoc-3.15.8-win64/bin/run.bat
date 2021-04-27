@echo off

set protoExe=C:\Users\Administrator\Desktop\toys\gameserverDemo\gameserver1\gameserver\tools\protoc-3.15.8-win64\bin\protoc.exe
set _protoSrc=C:\Users\Administrator\Desktop\toys\gameserverDemo\gameserver1\gameserver\proto
set protoOut=C:\Users\Administrator\Desktop\toys\gameserverDemo\gameserver1\gameserver\src\main\java

cd %_protoSrc%

for /r %%i in (*.proto) do (
echo %%i
     %protoExe% --proto_path=%_protoSrc% --java_out=%protoOut% %%i
)
pause
