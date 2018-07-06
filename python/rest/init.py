import json
import requests
import time

BaseURL = 'http://localhost:8080/crmservice'

List_post_header = {'Content-Type': 'application/json;charset=UTF-8'}


def getTimeStamp():
    nowTime = lambda: int(round(time.time() * 1000))
    return nowTime()


def printAI(uri, response):
    print(uri, ":", response.json(), "\n")


def postAI(uri, payload):
    response = requests.post(url=BaseURL + uri, data=json.dumps(payload), headers=List_post_header)
    printAI(uri, response)
    return response
