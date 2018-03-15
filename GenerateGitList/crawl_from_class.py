import requests
import re
import json
import math
from bs4 import BeautifulSoup

HOMEWORK_ID = '1645'
PATTERN = re.compile(r'(?P<Number>.+)[\s\n\t\r]*\+[\s\n\t\r]*(?P<BlogURL>.+)[\s\n\t\r]*\+[\s\n\t\r]*(?P<Coding>.+)',
                     flags=re.MULTILINE)


def get_comments_body(homework_id):
    request_template = 'https://edu.cnblogs.com/Homework/GetComments?homeworkId={0}'
    request_url = request_template.format(homework_id)
    # 请求路由，获取评论页内容，返回为JSON格式
    content = requests.get(request_url).content.decode('utf8')
    obj = json.loads(content)
    return obj


def match_groups(comments):
    for index, comment in enumerate(comments):
        content = comment["Content"]
        mat = PATTERN.search(content)
        try:
            github_url = mat.group('Number')
            number = mat.group('BlogURL')
            blog_url = mat.group('Coding')
        except Exception as e:
            print('第{0}楼的格式不正确\nDetails:{1}'.format(index + 1, content))
        else:
            print('\t'.join([github_url, number, blog_url]))


if __name__ == '__main__':
    comments = get_comments_body(homework_id=HOMEWORK_ID)
    match_groups(comments=comments)
