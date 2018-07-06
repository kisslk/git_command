import urllib.request
import urllib.parse
import urllib.error
import http.cookiejar
import reptile_init

url='http://bbs.chinaunix.net/member.php?mod=logging&action=login&loginsubmit=yes&loginhash=La2A2'

data={
    'username':'zhanghao',
    'password':'mima'
}

postdata=urllib.parse.urlencode(data).encode('utf8')
header={
    'User-Agent':'Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'
}

def get_cookie():
    # 使用http.cookiejar.CookieJar()创建CookieJar对象
    cjar = http.cookiejar.CookieJar()
    # 使用HTTPCOOKieProcessor创建cookie处理器，并以其为参数构建opener对象
    cookie = urllib.request.HTTPCookieProcessor(cjar)
    return cookie

opener=urllib.request.build_opener(get_cookie())
#将opener安装为全局
urllib.request.install_opener(opener)

try:
    request=urllib.request.Request(url, postdata, headers=header)
    response = urllib.request.urlopen(request)
except urllib.error.HTTPError as e:
    print(e.code)
    print(e.reason)

reptile_init.write_file('test1.html', response.read())

htmlcode = reptile_init.get_html('http://bbs.chinaunix.net/forum-327-1.html')
reptile_init.write_file('test2.html', htmlcode)
