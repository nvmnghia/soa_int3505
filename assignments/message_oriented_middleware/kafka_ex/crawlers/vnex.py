import re
from requests import get
from bs4 import BeautifulSoup
from json import loads


class VnExCrawler():
    @classmethod
    def is_crawlable(cls, url):
        return re.match('https?:\/\/(www\.)?vnexpress\.net\/.*[0-9]\.html', url)


    @classmethod
    def get_url_from_id(cls, article_id):
        info = get(f'https://gw.vnexpress.net/ar/get_basic?article_id={article_id}&data_select=share_url')
        info = loads(info.text)
        return info['data'][0]['share_url']


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
            item['title']   = html.select_one('h1.title-detail').text
            item['descpt']  = html.select_one('p.description').text
            item['content'] = html.select_one('article').text
            item['related']   = []
            crawled.append(item)

            # Crawl the related
            article_ids = html.select_one('div.list_link')['data-component-value'].split(',')
            for article_id in article_ids:
                article_url = VnExCrawler.get_url_from_id(article_id)
                related_articles = VnExCrawler.crawl(article_url, depth - 1)
                crawled.extend(related_articles)
                item['related'].append(article_url)

            return crawled
        except:
            return []
