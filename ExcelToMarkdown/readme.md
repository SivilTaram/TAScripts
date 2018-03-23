# 概况

本工具用于将Excel表格转换为Markdown语法，常在发表评分博客时使用。对开发者@fanfeilong 表示由衷感谢。

# 使用

在Windows平台下，使用命令行([如何使用?](https://blog.csdn.net/zuliang001/article/details/49705469))运行 `exceltk.exe` 文件，运行参数与说明如下，其中 `xxx.xlsx` 即要转换为MarkDown语法的Excel文件名。

- 整个表格
    - `exceltk.exe -t md -xls xxx.xls`
    - `exceltk.exe -t md -xls xxx.xlsx`

如果想指定生成某个sheet，可使用如下命令。

- 指定sheet
    - `exceltk.exe -t md -xls xx.xls -sheet sheetname`
    - `exceltk.exe -t md -xls xx.xlsx -sheet sheetname`

