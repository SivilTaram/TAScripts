# 概况

本工具用于自动下载学生的Git仓库，基本原理为：
1. 按照给定列表 blog.txt 爬取博客作业内容
2. 利用自定义规则文件 config.txt(默认抽取Github) 抽取博客中的 git 仓库地址
3. 利用 .NET Git的动态链接库自动克隆项目，并将克隆的文件夹自动命名为学生学号

# 使用

在Windows平台下，使用命令行([如何使用?](https://blog.csdn.net/zuliang001/article/details/49705469))运行 `autoclone.exe` 文件，运行参数与说明如下，其中 `xxx.xlsx` 即要转换为MarkDown语法的Excel文件名。

- 整个表格
    - `exceltk.exe -t md -xls xxx.xls`
    - `exceltk.exe -t md -xls xxx.xlsx`

如果想指定生成某个sheet，可使用如下命令。

- 指定sheet
    - `exceltk.exe -t md -xls xx.xls -sheet sheetname`
    - `exceltk.exe -t md -xls xx.xlsx -sheet sheetname`

