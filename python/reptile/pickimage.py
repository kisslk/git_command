import urllib.request
import re
import reptile_init
import time


def get_image(htmlcode):
    reg = r'src="(.+?\.jpg)" width'
    reg_image = re.compile(reg)  # 编译一下，运行更快
    imglist = reg_image.findall(htmlcode)  # 进行匹配
    for img in imglist:
        print(img)
        urllib.request.urlretrieve(img, 'd:\\temp\image\%s.jpg' % reptile_init.getTimeStamp())

print(u'------------网页图片抓取-----------')
print(u'请输入url:')
url = input()
if url:
    pass
else:
    print(u'----没有地址输入正在使用默认地址----')
    url='http://tieba.baidu.com/p/1753935195'

print(u'-----------正在获取网页----------')
htmlcode = reptile_init.get_html(url)
print(u'-----------正在下载图片----------')
get_image(htmlcode)
print(u'-----------下载成功-----------')
input('Press Enter to exit')
