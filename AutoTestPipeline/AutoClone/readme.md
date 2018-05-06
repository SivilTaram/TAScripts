# 概况

本工具用于自动下载学生的Git仓库，基本原理为：
1. 按照给定列表 blog.txt 爬取博客作业内容
2. 利用自定义规则(默认抽取coding.net)抽取博客中的 git 仓库地址
3. 利用 .NET Git的动态链接库自动克隆项目，并将克隆的文件夹自动命名为学生学号

克隆完成后，Git仓库在本目录的文件夹 `Projects` 下, 日志在文件夹 `Log` 下, Github项目链接映射表在 `RepoMap.txt` 中。

# 使用

解压缩本目录下的`windows_binary.zip`压缩包。在班级博客中导出项目对应作业的提交列表，参考`blog.txt`的格式做出相应修改，每行以`\t`分隔。在Windows平台下，使用命令行([如何使用?](https://blog.csdn.net/zuliang001/article/details/49705469))运行 `autoclone.exe` 文件，运行参数与说明如下：

```shell
autoclone.exe -blog [blog file] -mode [mode] -num [number id] -pat [git pattern]
```
- [blog file] 提供学号与作业地址的对应关系, 多行分开。如不指定该参数则默认为当前目录 blog.txt。
   每行的格式如: 031502334   http://cnblogs.com/easteast/p/1234.html
- [git pattern] 提供仓库地址的正则表达式, 默认为克隆 coding.Net, 样例: `https://git.coding.net/.+?\\.git`。这里值得注意的一点是需要使用正则表达式的非贪婪模式`.+?`，否则可能会匹配到形如`https://git.coding.net/....<a>https://git.coding.net/.../xxx.git`。
- [mode] 指定测试选用的模式，目前提供两种选择：
    - a : 跳过当前 Projects目录下已有工程, 分析未成功克隆项目同学的博客 ,并将项目克隆到本地。
    - w : 将 Projects 文件夹重命名，重新爬取所有学生的博客并将项目克隆到 Projects文件夹下。
- [number id] 提供单个学号, 当本参数存在时, 将只抓取单个同学的博客并重新克隆工程。

如都选择默认选项，直接使用

```shell
autoclone.exe
```

即可。