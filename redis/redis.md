redis 
-

- redis实现分布式锁
  - aop拦截annotation(type=method)
  - 获取参数annotation(type=field, parameter)
  - 多个参数以下划线分隔，每个参数值必须加key
  - 加锁使用redis.setnx
  - 解锁使用lua脚本
  - 保证原子性操作
  - 已经实现，后期整合成一个基础服务，用户在maven里加载jar包并配置，就可以无缝使用
