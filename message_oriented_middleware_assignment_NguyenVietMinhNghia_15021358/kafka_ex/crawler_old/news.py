import scrapy


class News(scrapy.Item):
    url     = scrapy.Field()
    title   = scrapy.Field()
    descpt  = scrapy.Field()
    content = scrapy.Field()
    referer = scrapy.Field()
