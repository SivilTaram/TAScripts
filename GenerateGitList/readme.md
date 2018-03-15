# 使用说明

本文件夹中的Scripts用于收集同学们学号、博客与Git账号的对应关系。

1. `crawl_from_blog.py` 文件用于爬取博文下的评论。
示例：http://www.cnblogs.com/easteast/p/7403491.html

2. `crawl_from_class.py` 文件用于爬取班级博客下的评论。
示例：https://edu.cnblogs.com/campus/nenu/2016SE_NENU/homework/1645

# 开始

本项目使用python3编写，在安装好python3后，使用

```python
pip install -r requirements.txt
```

即可安装本项目的依赖库`requests`与`bs4`。

找到班级所需版本的脚本，直接使用

```python
pip install -r requirements.txt
```

依赖安装完成后，直接运行脚本即可。

```python
python crawl_from_x.py
```