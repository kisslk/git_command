#### 16年就使用了jpa，最近又研究了一番，最终还是弃用jpa，坚持mybatis。
- mybatis
  - mybatis更灵活，sql不是问题
  - mybatis可维护性更高
  - mybatis代码与sql分开更清晰，舒服
  - mybatis有代码生成插件，对于单表操作非常方便
- jpa
  - jpa不好维护
  - jpa复杂查询比较麻烦
  - jpa代码不整洁

- tx + mybatis
  - 最终使用tx+mybatis，tx解决简单读写，mybatis解决稍微复杂的sql
  - 使用搜索引擎解决高并发查询
