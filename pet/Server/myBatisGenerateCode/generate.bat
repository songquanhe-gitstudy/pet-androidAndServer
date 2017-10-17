cd src
@echo off
for /f  "tokens=*" %%a in ('dir /s/b/a-d') do (del /s /q %%a)
cd ..
cd ..
java -jar mybatis-generator-core-1.3.5.jar -configfile myBatisGenerateCode/generateConfig/generateConfig.xml -overwrite
cd myBatisGenerateCode