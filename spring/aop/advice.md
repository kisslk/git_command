### aop切面

- 在切面往RequestBody参数织入session信息，发现先被validate拦截，校验失败
- 使用RequestBodyAdvice（主要是在HttpMessageConverter处理request body的前后做一些处理，和body为空的时候做处理。）
- 这样既不需要在拦截器里重写流，也显得更简洁优美
