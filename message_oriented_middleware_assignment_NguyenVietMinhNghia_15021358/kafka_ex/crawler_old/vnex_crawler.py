import scrapy
from scrapy.spiders import CrawlSpider, Rule
from scrapy.linkextractors import LinkExtractor
from scrapy.link import Link

from unicodedata import normalize
from bs4 import BeautifulSoup
from requests import get
from json import loads

from crawler.news import News


class VnExLinkExtractor(LinkExtractor):
    def extract_links(self, response):
        article_ids = response \
                        .css('div.list_link::attr(data-component-value)')[0].get() \
                        .split(',')
        links = []

        for article_id in article_ids:
            info = get(f'https://gw.vnexpress.net/ar/get_basic?article_id={article_id}&data_select=share_url')
            info = loads(info.text)
            url = info['data'][0]['share_url']
            links.append(Link(url))

        return links


class VnExSpider(CrawlSpider):
    name = 'vnex'
    allowed_domains = ['vnexpress.net']
    rules = (
        Rule(
            callback='parse_item',
        ),
        Rule(
            link_extractor=VnExLinkExtractor(tags='div', attrs='data-component-value'),
            callback='parse_item'
        )
    )


    def __init__(self, url, depth=5, name=None, **kwargs):
        super().__init__(name=name, **kwargs)

        self.start_urls = [url]

        # Doesn't work with CrawlSpider
        self.custom_settings = { 'DEPTH_LIMIT': depth }

        # Set a middleware instead
        # SPIDER_MIDDLEWARES = {
        #     'crawler.StickyDepthSpiderMiddleware': depth,
        # }


    def parse_start_url(self, response):
        return self.parse_item(response)


    def parse_item(self, response):
        item = News()
        item['url']     = response.url
        try:
            item['referer'] = response.request.headers['referer']
        except:
            pass
        item['title']   = normalize('NFKD', ''.join(response.css('h1.title-detail::text').extract())).strip()
        item['descpt']  = normalize('NFKD', ''.join(response.css('p.description::text').extract())).strip()
        item['content'] = normalize('NFKD', ''.join(response.css('article>p::text').extract())).strip()

        if (response.meta['depth'] > 1):
            print('fuck')

        return item
