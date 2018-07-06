import urllib.request
import urllib.parse
import random

header = [('User-Agent', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36')]
iplist=['115.32.41.100:80','58.30.231.36:80','123.56.90.175:3128']

def use_proxy(url):
    proxy = urllib.request.ProxyHandler({'http':random.choice(iplist)})
    opener = urllib.request.build_opener(proxy, urllib.request.HTTPHandler)
    opener.addheaders = header
    urllib.request.install_opener(opener)
    response = urllib.request.urlopen(url)
    data = response.read().decode('utf8')
    return data

data = use_proxy("http://www.baidu.com")
print(data)
