# Sharding读写分离

### 添加sharding的spring-boot-starter的依赖

sharding的读写分离配置相当简单

```xml

<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
    <version>4.1.1</version>
</dependency>
```

### 添加依赖后添加如下配置

1.properties配置方式

```properties
# 数据源。启用sharding之后会覆盖该配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:13306/master
spring.datasource.username=root
spring.datasource.password=root
# sharding配置
# 多数据源定义
spring.shardingsphere.datasource.names=master,slave0,slave1
# 数据源1
spring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://127.0.0.1:13306/master
spring.shardingsphere.datasource.master.username=root
spring.shardingsphere.datasource.master.password=root
# 连接池类型，可以自己定义
spring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource 
# 数据源2
spring.shardingsphere.datasource.slave0.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.slave0.jdbc-url=jdbc:mysql://127.0.0.1:13306/slave0
spring.shardingsphere.datasource.slave0.username=root
spring.shardingsphere.datasource.slave0.password=root
spring.shardingsphere.datasource.slave0.type=com.zaxxer.hikari.HikariDataSource
# 数据源3
spring.shardingsphere.datasource.slave1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.slave1.jdbc-url=jdbc:mysql://127.0.0.1:13306/slave1
spring.shardingsphere.datasource.slave1.username=root
spring.shardingsphere.datasource.slave1.password=root
spring.shardingsphere.datasource.slave1.type=com.zaxxer.hikari.HikariDataSource
# 读写分离定义
spring.shardingsphere.masterslave.name=ms
# 定义写库
spring.shardingsphere.masterslave.master-data-source-name=master
# 定义读库
spring.shardingsphere.masterslave.slave-data-source-names=slave0,slave1
# 打印sql
spring.shardingsphere.props.sql.show=true
# 启动sharding配置
spring.shardingsphere.enabled=true
# 日志
logging.level.com.sharding.demo=trace
```

2.环境变量配置方式

注意区分下换线和减号 (`_`and`-`)

```
SPRING_SHARDINGSPHERE_DATASOURCE_NAMES=master,slave0,slave1
SPRING_SHARDINGSPHERE_DATASOURCE_MASTER_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_SHARDINGSPHERE_DATASOURCE_MASTER_JDBC_URL=jdbc:mysql://127.0.0.1:13306/master
SPRING_SHARDINGSPHERE_DATASOURCE_MASTER_USERNAME=root
SPRING_SHARDINGSPHERE_DATASOURCE_MASTER_PASSWORD=root
SPRING_SHARDINGSPHERE_DATASOURCE_MASTER_TYPE=com.zaxxer.hikari.HikariDataSource
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE0_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE0_JDBC_URL=jdbc:mysql://127.0.0.1:13306/slave0
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE0_USERNAME=root
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE0_PASSWORD=root
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE0_TYPE=com.zaxxer.hikari.HikariDataSource
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE1_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE1_JDBC_URL=jdbc:mysql://127.0.0.1:13306/slave1
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE1_USERNAME=root
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE1_PASSWORD=root
SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE1_TYPE=com.zaxxer.hikari.HikariDataSource
SPRING_SHARDINGSPHERE_MASTERSLAVE_NAME=ms
SPRING_SHARDINGSPHERE_MASTERSLAVE_MASTER_DATA_SOURCE_NAME=master
SPRING_SHARDINGSPHERE_MASTERSLAVE_SLAVE_DATA_SOURCE_NAMES=slave0,slave1
SPRING_SHARDINGSPHERE_PROPS_SQL_SHOW=true
```

### 进行读写分离的测试

#### 普通查询测试

```java
@Test
public void getList(){
        keyTestDao.getCount();
        }
```

打印日志如下：

```log
2021-12-20 19:23:52.857 DEBUG 16412 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : ==>  Preparing: select count(*) from key_test
2021-12-20 19:23:53.329  INFO 16412 --- [           main] ShardingSphere-SQL                       : Logic SQL: select count(*) from key_test
2021-12-20 19:23:53.329  INFO 16412 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@4ed19540, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@533e8807), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@533e8807, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=14, distinctRow=false, projections=[AggregationProjection(type=COUNT, innerExpression=(*), alias=Optional.empty, derivedAggregationProjections=[], index=-1)]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@322eb1a, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@2b4ba2d9, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@69f24965, containsSubquery=false)
2021-12-20 19:23:53.329  INFO 16412 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave0 ::: select count(*) from key_test
2021-12-20 19:23:53.442 DEBUG 16412 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : ==> Parameters: 
2021-12-20 19:23:53.531 TRACE 16412 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==    Columns: count(*)
2021-12-20 19:23:53.532 TRACE 16412 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==        Row: 5
2021-12-20 19:23:53.540 DEBUG 16412 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==      Total: 1
```

可以看到查询的是`slave0`这个库

#### 普通插入查询

```java
@Test
public void insert(){
        keyTestDao.insert();
        }
```

打印日志如下：

```
2021-12-20 19:30:52.722 DEBUG 7064 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : ==>  Preparing: insert into key_test(`key`) values (1)
2021-12-20 19:30:53.121  INFO 7064 --- [           main] ShardingSphere-SQL                       : Logic SQL: insert into key_test(`key`) values (1)
2021-12-20 19:30:53.121  INFO 7064 --- [           main] ShardingSphere-SQL                       : SQLStatement: InsertStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.InsertStatement@61d61b0e, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@3c2188f), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@3c2188f, columnNames=[key], insertValueContexts=[InsertValueContext(parametersCount=0, valueExpressions=[LiteralExpressionSegment(startIndex=36, stopIndex=36, literals=1)], parameters=[])], generatedKeyContext=Optional.empty)
2021-12-20 19:30:53.121  INFO 7064 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: insert into key_test(`key`) values (1)
2021-12-20 19:30:53.653 DEBUG 7064 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : ==> Parameters: 
2021-12-20 19:30:53.819 DEBUG 7064 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : <==    Updates: 1
```

可以看到插入数据时走的是主库。

#### 同一事物情况下先插入后查询

```java
@Test
public void InterAfterGet(){
        keyTestService.InterAfterGet();
        }

public void InterAfterGet(){
        keyTestDao.insert();
        keyTestDao.getCount();
        }
```

日志如下

```
2021-12-20 19:37:11.984 DEBUG 17292 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : ==>  Preparing: insert into key_test(`key`) values (1)
2021-12-20 19:37:12.399  INFO 17292 --- [           main] ShardingSphere-SQL                       : Logic SQL: insert into key_test(`key`) values (1)
2021-12-20 19:37:12.399  INFO 17292 --- [           main] ShardingSphere-SQL                       : SQLStatement: InsertStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.InsertStatement@5552d10, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@3b3190fd), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@3b3190fd, columnNames=[key], insertValueContexts=[InsertValueContext(parametersCount=0, valueExpressions=[LiteralExpressionSegment(startIndex=36, stopIndex=36, literals=1)], parameters=[])], generatedKeyContext=Optional.empty)
2021-12-20 19:37:12.399  INFO 17292 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: insert into key_test(`key`) values (1)
2021-12-20 19:37:13.854 DEBUG 17292 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : ==> Parameters: 
2021-12-20 19:37:14.043 DEBUG 17292 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : <==    Updates: 1
2021-12-20 19:37:14.048 DEBUG 17292 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : ==>  Preparing: select count(*) from key_test
2021-12-20 19:37:14.139  INFO 17292 --- [           main] ShardingSphere-SQL                       : Logic SQL: select count(*) from key_test
2021-12-20 19:37:14.139  INFO 17292 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@3bd79444, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@5671078e), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@5671078e, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=14, distinctRow=false, projections=[AggregationProjection(type=COUNT, innerExpression=(*), alias=Optional.empty, derivedAggregationProjections=[], index=-1)]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@3fd21272, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@4b4bc73d, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@4cb91fa4, containsSubquery=false)
2021-12-20 19:37:14.139  INFO 17292 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: select count(*) from key_test
2021-12-20 19:37:14.140 DEBUG 17292 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : ==> Parameters: 
2021-12-20 19:37:14.229 TRACE 17292 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==    Columns: count(*)
2021-12-20 19:37:14.229 TRACE 17292 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==        Row: 19
2021-12-20 19:37:14.239 DEBUG 17292 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==      Total: 1
```

通过日志可以看到，插入之后同一事物内的查询方法会强制走主库。

#### 同一事物下先查询后插入

同一事物下的先查询后插入如果需要都走主库的话需要手动在代码里指定强制走主库

```java
@Test
public void getAfterInsert(){
        keyTestService.getAfterInsert();
        }

public void getAfterInsert(){
        // hintManager实现了AutoCloseable接口
        try(HintManager instance=HintManager.getInstance()){
        // 强制使用主库，如果spring.sharding.enabled为false。该代码会自动失效，不会抛出异常
        instance.setMasterRouteOnly();
        keyTestDao.getCount();
        keyTestDao.insert();
        }catch(Exception e){
        throw new RuntimeException(e);
        }
        }
```

日志如下

```
2021-12-20 19:56:36.983 DEBUG 3944 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : ==>  Preparing: select count(*) from key_test
2021-12-20 19:56:37.501  INFO 3944 --- [           main] ShardingSphere-SQL                       : Logic SQL: select count(*) from key_test
2021-12-20 19:56:37.501  INFO 3944 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement@4e7151b3, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@5ef7ae2f), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@5ef7ae2f, projectionsContext=ProjectionsContext(startIndex=7, stopIndex=14, distinctRow=false, projections=[AggregationProjection(type=COUNT, innerExpression=(*), alias=Optional.empty, derivedAggregationProjections=[], index=-1)]), groupByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.groupby.GroupByContext@1bcf2c64, orderByContext=org.apache.shardingsphere.sql.parser.binder.segment.select.orderby.OrderByContext@b5bddfe, paginationContext=org.apache.shardingsphere.sql.parser.binder.segment.select.pagination.PaginationContext@4fe3f9ef, containsSubquery=false)
2021-12-20 19:56:37.501  INFO 3944 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: select count(*) from key_test
2021-12-20 19:56:37.717 DEBUG 3944 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : ==> Parameters: 
2021-12-20 19:56:37.803 TRACE 3944 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==    Columns: count(*)
2021-12-20 19:56:37.803 TRACE 3944 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==        Row: 19
2021-12-20 19:56:37.810 DEBUG 3944 --- [           main] c.sharding.demo.dao.KeyTestDao.getCount  : <==      Total: 1
2021-12-20 19:56:37.814 DEBUG 3944 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : ==>  Preparing: insert into key_test(`key`) values (1)
2021-12-20 19:56:37.829  INFO 3944 --- [           main] ShardingSphere-SQL                       : Logic SQL: insert into key_test(`key`) values (1)
2021-12-20 19:56:37.829  INFO 3944 --- [           main] ShardingSphere-SQL                       : SQLStatement: InsertStatementContext(super=CommonSQLStatementContext(sqlStatement=org.apache.shardingsphere.sql.parser.sql.statement.dml.InsertStatement@1ee52741, tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@2f5a092e), tablesContext=org.apache.shardingsphere.sql.parser.binder.segment.table.TablesContext@2f5a092e, columnNames=[key], insertValueContexts=[InsertValueContext(parametersCount=0, valueExpressions=[LiteralExpressionSegment(startIndex=36, stopIndex=36, literals=1)], parameters=[])], generatedKeyContext=Optional.empty)
2021-12-20 19:56:37.829  INFO 3944 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: insert into key_test(`key`) values (1)
2021-12-20 19:56:37.831 DEBUG 3944 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : ==> Parameters: 
2021-12-20 19:56:38.019 DEBUG 3944 --- [           main] com.sharding.demo.dao.KeyTestDao.insert  : <==    Updates: 1
```

如预期展示一致。通过代码指定可以在插入之前的查询也可以使用主库进行。