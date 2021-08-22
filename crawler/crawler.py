import urllib
from lxml.html import etree
from selenium import webdriver
import time


# generate url with base url and params
def generate_url(number):
    # assign language, page, keyword, type
    # keyword: require(fabric-shim)
    # data = {'l': 'JavaScript', 'p': number, 'q': 'require(fabric-shim)', 'size': '>10000', 'type': 'Code'}
    # keyword: require(fabric-contract-api)
    # data = {'l': 'JavaScript', 'p': number, 'q': 'require(fabric-contract-api)', 'type': 'Code'}
    # url_params = urllib.parse.urlencode(data)
    url_params = "l=JavaScript&p=" + str(number) + "&q=require(fabric-shim)+size%3A>10000&type=Code"
    base_url = 'https://github.com/search?'
    full_url = base_url + url_params
    full_url = urllib.parse.unquote(full_url)
    print(full_url)
    return full_url


# get content for specific url
def get_content(full_url):
    driver.get(full_url)
    content = driver.page_source
    try:
        content = content.encode('UTF-8').decode('UTF-8')
    except:
        print("解码失败")
    else:
        print("解码...")
    # construct xpath object and correct html text
    content = etree.HTML(content)
    return content


# generate 10 links within each page for further crawling
def generate_link(content):
    # find the whole code area
    code_list = content.xpath("/html/body/div[4]/main/div/div[3]/div/div[2]/div[1]/*")
    # 10 links within the whole code area
    for link_index in range(len(code_list)):
        url = code_list[link_index].xpath("./div[1]/div[2]/a/@href")
        url = "https://github.com" + url[0]
        check(url)


# get JavaScript file
def get_file_content(count):
    driver.get(urls[count])
    time.sleep(2)
    print(urls[count])
    content = driver.page_source
    try:
        content = content.encode('UTF-8').decode('UTF-8')
    except:
        print("爬取成功")
    else:
        print("继续...")
    content = etree.HTML(content)
    # find file label
    file = content.xpath("/html/body/div[4]/div/main/div[3]/div/div[3]/div[2]/table/tbody/*")

    # handle with archived file
    if len(file) <= 0:
        file = content.xpath("/html/body/div[4]/div/main/div[4]/div/div[3]/div[2]/table/tbody/*")

    # extract file content by row
    file_content = []
    for row_num in range(len(file)):
        row = ''.join(file[row_num].xpath(".//td[2]//text()"))
        file_content.append(row + "\r\n")
    # transform into string
    file_content = ''.join(file_content)
    return file_content


# write content into new files
def save(file_content):
    filename = time.strftime('%Y%m%d-%H%M%S ', time.localtime(time.time()))
    filename = str(filename)
    file = r"D:\graduation\crawler\shim\\" + filename + ".js"
    f = open(file, 'a+')
    f.write(file_content)
    f.close()


# check whether the file is js file
def check(url):
    if url.endswith("js"):
        urls.append(url)


if __name__ == "__main__":
    baseurl = "https://github.com/login"
    chrome_driver = 'E:\\chromedriver.exe'
    driver = webdriver.Chrome(executable_path=chrome_driver)
    driver.get(baseurl)
    driver.find_element_by_id("login_field").send_keys("13889384135@163.com")
    driver.find_element_by_id("password").send_keys("1003love0719")
    driver.find_element_by_name("commit").click()
    time.sleep(20)
    i = 0
    s = 0
    k = 0
    # Github search results are limited to 100 pages
    # need to narrow 'size' qualifier, eg. size:5000..10000, between 5kb and 10kb
    for page_num in range(35):
        urls = []
        generate_link(get_content(generate_url(page_num + 1)))
        print("-----------------第%d页" % (page_num + 1))
        # print(urls)
        # 10 files each page
        for file_num in range(len(urls)):
            i = i + 1
            try:
                js_file = get_file_content(file_num)
                if js_file:
                    s = s + 1
                    save(js_file)
                else:
                    k = k + 1
                    print("-----------------empty file")
            except:
                print("-----------------写入失败...")
            else:
                print("-----------------继续抓取")
            print("第%d页：正在抓取第%d个文件,已经抓取到%s个文件，空数据文件%d个，抓取下一个文件..." % ((page_num + 1), i, s, k))
    print("-----------------此次数据爬取已完成！--------------")
