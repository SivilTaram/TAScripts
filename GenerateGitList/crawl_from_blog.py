import requests
import re
import json
import math
from bs4 import BeautifulSoup

BLOG_APP = 'easteast'
POST_ID = '8401508'
PATTERN = re.compile(r'(?P<GithubURL>.+)[\s\n\t\r]+(?P<Number>.+)[\s\n\t\r]+(?P<BlogURL>.+)', flags=re.MULTILINE)


def get_comments_body(blog_name, post_id):
    request_template = 'http://www.cnblogs.com/mvc/blog/GetComments.aspx?postId={0}&blogApp={1}&pageIndex={2}'
    page_limit = 1
    page_index = 1
    comment_html = ''
    while page_index <= page_limit:
        request_url = request_template.format(post_id, blog_name, page_index)
        # 请求路由，获取评论页内容，返回为JSON格式
        content = requests.get(request_url).content.decode('utf8')
        obj = json.loads(content)
        comment_html += obj['commentsHtml']
        # obj中的commentCount参数指明总共有多少条评论, 1页最多可以显示50条评论
        if page_limit == 1:
            page_limit = math.ceil(obj['commentCount'] / 50.0)
        page_index += 1
    return comment_html


def match_groups(comment_html):
    comment_html = re.sub(r'<br/>', '\n', comment_html)
    bs = BeautifulSoup(comment_html)
    comments = bs.find_all(class_="blog_comment_body")
    for index, comment in enumerate(comments):
        content = comment.text
        mat = PATTERN.search(content)
        try:
            github_url = mat.group('GithubURL')
            number = mat.group('Number')
            blog_url = mat.group('BlogURL')
        except Exception as e:
            print('第{0}楼的格式不正确\nDetails:{1}'.format(index + 1, content))
        else:
            print('\t'.join([github_url, number, blog_url]))


if __name__ == '__main__':
    comments = get_comments_body(blog_name=BLOG_APP, post_id=POST_ID)
    match_groups(comment_html=comments)
