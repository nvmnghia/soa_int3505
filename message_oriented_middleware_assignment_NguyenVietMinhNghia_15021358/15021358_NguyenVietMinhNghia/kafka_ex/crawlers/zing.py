import re
from bs4 import BeautifulSoup
from requests import get


class ZingCrawler():

    @classmethod
    def is_crawlable(cls, url):
        return re.match('https?:\/\/(www\.)?zingnews\.vn\/.*post[0-9]+\.html', url)

    @classmethod
    def crawl(cls, url, depth=2):
        if depth == 0:
            return []

        try:
            print(f'Crawling {url}')
            crawled = []

            # Crawl the site
            html = BeautifulSoup(get(url).text, features='lxml')

            item = {}
            item['url']     = url
            item['title']   = html.select_one('h1.the-article-title').text
            item['descpt']  = html.select_one('p.the-article-summary').text
            item['content'] = html.select_one('div.the-article-body > p').text
            item['related']   = []
            crawled.append(item)

            # Crawl the related
            for article_intro in html.select('div.inner-article > a'):
                article_url = 'https://zingnews.vn' + article_intro['href']
                related_articles = ZingCrawler.crawl(article_url, depth - 1)
                crawled.extend(related_articles)
                item['related'].append(article_url)

            return crawled
        except:
            return []
