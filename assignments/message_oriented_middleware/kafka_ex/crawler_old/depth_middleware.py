from scrapy import Request


class StickyDepthSpiderMiddleware:

    def process_spider_output(self, response, result, spider):
        key_found = response.meta.get('depth', None)
        for x in result:
            if isinstance(x, Request) and key_found is not None:
                x.meta.setdefault('depth', key_found)
            yield x
