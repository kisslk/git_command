- 获取json解析数据，并发送http-post请求

```bash
#!/bin/bash
json=`cat task | jq '.data[].taskKey'`
echo "taskKeys: $json"
for taskKey in $json
do
    echo "$taskkey"
    result=`curl -H "Content-Type:application/json" -X POST -d  '{"operatorKey":-1,"operatorName":"system","operatorType":2,"taskKey":'$taskKey'}' http://ip:port/context-path/cancel`
    echo $result
done
```
