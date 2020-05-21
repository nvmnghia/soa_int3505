from kafka import KafkaConsumer
from json import loads, dumps
from os.path import join
from unidecode import unidecode

from .. import config
from ..crawlers.zing import ZingCrawler


zing_con = KafkaConsumer(
    config.ZING_TOPIC)

for msg in zing_con:
    crawl_request = loads(msg.value)
    crawled = ZingCrawler.crawl(crawl_request['url'], crawl_request['depth'])

    if len(crawled) == 0:
        print(f"Can't crawl {crawl_request['url']}")
        continue
    else:
        print(f"Done crawling {crawl_request['url']}, got {len(crawled)} pages")

    # Write to file
    output_filename = unidecode(crawled[0]['title']) + '.json'

    try:
        output_path = join(crawl_request['output'], output_filename)
        f = open(output_path, 'w+')

        with f:
            f.write(dumps(crawled))
    except OSError:
        output_path = output_filename
        f = open(output_path, 'w+')

        with f:
            f.write(dumps(crawled))
