# 动态编译(Java版)

## 概况

动态编译是指使用程序动态读入源文件，将其编译成可执行文件的过程。本项目使用`Java`语言，实现了动态编译`Java`工程并可生成`.class`文件，且可导出出错的编译记录。源代码为`JavaCompiler`文件夹，工程使用[IntelliJ IDEA](https://www.jetbrains.com/idea/)完成，如有定制化需求请自行克隆代码更改。


## 使用

TODO:由于动态编译依赖于JDK版本的Java Runtime Environment独有的tools.jar包，目前作者还未找到合适的方式将导出的jar包在无JDK开发环境的情况下使用，正在实验中，请耐心等待:)