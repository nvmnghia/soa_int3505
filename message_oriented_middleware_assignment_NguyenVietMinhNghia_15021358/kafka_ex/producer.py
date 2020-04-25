from kafka import KafkaProducer
import os
import re
from json import dumps

from . import config


def get_news_source(url):
    """
    Detect if the URL is a VnExpress or Zing article
    """

    if re.match('https?:\/\/(www\.)?vnexpress\.net\/.*[0-9]\.html', url):
        return config.VNEX_TOPIC
    elif re.match('https?:\/\/(www\.)?zingnews\.vn\/.*post[0-9]+\.html', url):
        return config.ZING_TOPIC
    else:
        return None


print('Enter an URL of either VnExpress or Zing article: ', end='')
url = input()
print('Enter crawl depth: ', end='')
depth = int(input())

news_source = get_news_source(url)
if news_source is None:
    print(f'The URL is not a VnExpress or Zing article')
    exit()
else:
    cwd = os.getcwd()
    print(f"The URL is a {'VnExpress' if news_source == config.VNEX_TOPIC else 'Zing'} article.")
    print("It will be sent to {news_source} topic to be crawled.")
    print("The result in json will be placed at this directory {cwd}")
    print("or consumer's folder if it doesn't exist.")

    payload = {}
    payload['url'] = url
    payload['depth'] = depth
    payload['output'] = cwd

    producer = KafkaProducer(
        bootstrap_servers=config.SERVER,
        value_serializer=lambda x: dumps(x).encode('utf-8'))
    producer.send(news_source, payload)
    producer.close()
