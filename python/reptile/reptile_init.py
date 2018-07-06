import urllib.request
import urllib.parse

header = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36'}

def get_request(url):
    return urllib.request.Request(url, headers=header)

def get_html(url):

    page = urllib.request.urlopen(get_request(url))
    html = page.read()
    return html

def write_file(file, content):
    pageFile = open(file, 'wb')
    pageFile.write(content)
    pageFile.close()


htmlcode = get_html('http://tieba.baidu.com/p/1753935195')
write_file('pageCode.txt', htmlcode)
