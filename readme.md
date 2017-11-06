# 基于redis的布隆过滤器

## 简介

BloomFilterRedis:使用Redis的Bitmap作为位数组构建起来的可扩展的布隆过滤器，位数组的默认长度为2^28，哈希函数默认为八个。

### 开发环境

- springMvc 4.3.6
- redis 2.8.17

### 使用方法

对外提供 BloomFilter-Api/check 接口，来判断待验证内容是否已经存在。

#### 接口说明

- 支持的格式

  ​	json

- http请求方式

  ​	post

- 请求参数

|      | Requires | Type and Range | Description   |
| ---- | -------- | -------------- | ------------- |
| text | true     | String         | 待验证数据，只支持单条数据 |

- 注意

  ​	传入数据大小限制为1MB

- 结果

```
存在：
{"result":true}
不存在
{"result":false}
error:
{"result":false,"errorCode":errorCode,"errorMsg":""}

errorCode  1  "系统内部错误"

```





