import init

print(">>>>>>>>>>>>>>>>>>>>>>CallRecord test start>>>>>>>>>>>>>>>>>>>");


def create():
    # 创建通话记录
    payload = {
        "callNo": init.getTimeStamp(),
        "busiCode": 0,
        "groupCode": 0,
        "callDt": "2018-07-06T03:36:56.308Z",
        "callTimes": 0,
        "userMobile": "string",
        "callType": 0,
        "answerStatus": 0,
        "customerId": 0,
        "satisfyStatus": 0,
        "qaTask": 0,
        "qaTaskNo": "string",
        "link": "string"
    }
    init.postAI('/callRecord/create', payload)


def search():
    # 查询通话记录
    payload = {
        "busiCode": 0,
        "groupCode": 0,
        "pageIndex": 0,
        "pageSize": 2
    }
    init.postAI('/callRecord/search', payload)


def satisfy():
    # 增加满意度
    payload = {
        "callNo": 1001,
        "satisfyStatus": 1
    }
    init.postAI('/callRecord/satisfy', payload)


create()
search()
satisfy()

print("<<<<<<<<<<<<<<<<<<<<<CallRecord test end<<<<<<<<<<<<<<<<<<<<<");
