- 用Dockerfile构建镜像
docker build -t tomcat .  
docker build -t centos:7tarsz .  

- 进入容器内部
docker run -i -t --net=host --privileged=true tars:16 /bin/bash  
docker run -d --net=host -p 30011:8080 --privileged=true --name myspring springk8s:latest  

  docker attach 7e15322fae59（一个bash）  

  -d --net=host  
  --net=host 容器和虚拟机共享网络还有其他三种方式  
  -d 后台启动方式对容器的操作可以保留  
  
  1070 docker run -i -t indigo/centos-maven /bin/bash  
  1071 docker run -it --net=host indigo/centos-maven /bin/bash  
  1072 history  
  docker exec -it registry /bin/bash
  
  
- 删除镜像
docker rmi tars/5:5(一定是名字)   

- Docker 问答录
[100问](https://blog.lab99.org/post/docker-2016-07-14-faq.html)   

docker build -t zingdocker/jdk-tomcat .  

- Docker[基本命令使用详解](http://blog.csdn.net/tianpy5/article/details/52336166) 

$ sudo docker run --restart=always --name my_container -d ubuntu /bin/bash  

--restart 标志会检查容器的退出代码，并据此来决定是否要重启容器，默认是不会重启。   
--restart的参数说明   
always: 无论容器退出的代码是什么，Docker都会自动重启该容器。   
on-failure: 只有当容器的退出代码为非0值得时候才会自动重启。另外，该参数还接受一个可选的重启次数参数, 
--restart=on-failure:5表示当容器退出代码为非0时，Docker会尝试自动重启该容器，最多5次。   

-p 8080:8080  
第一个8080是容器内的port  
第二个8080是虚拟机的port  

- 1.12.x是老的版本号，17.x后就有ce版和ee版了

