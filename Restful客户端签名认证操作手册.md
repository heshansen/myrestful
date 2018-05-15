# Restful客户端签名认证操作手册

```
1.此文档包含javascript和java客户端签名认证使用说明,应用示例和接口调用说明.
2.只有当js和client生成的签名和服务器生成的签名一致时方能调用接口,否则拒绝访问.
3.签名生成方法及其密钥由淘璞平台统一提供.
```

## 一、javascript签名使用说明

* 必须引入js:

> hmac-sha1.js,restfulsha.js,jquery.base64.js

* 调用方法:

> restFulSha(RequestMethod, url, topbabysecret);
返回的是个对象:
返回签名的字段是:signature
返回过期时间的字段是:expires

* 参数说明:

> 
|**名称** | 类型 |描述|例如|
|:-----------|:------------|:------------|:------------|
|RequestMethod|String|请求方法|GET,POST,PUT等,必须大写
|url|String|需要加密的内容|/topbaby/restful/bss/qrCode/getBsPicture
|topbabysecret|String|淘璞帮分配的密钥|topbabysecret

* 应用示例:

```
1.先引入js:
<script src="./hmac-sha1.js" type="text/javascript"></script>
<script src="./restfulsha.js" type="text/javascript"></script>
<script src="./jquery.base64.js" type="text/javascript"></script>

2.再调用js中方法生成签名和过期时间
 var obj = restFulSha("GET", "/topbaby/restful/bss/qrCode/getBsPicture","topbabysecret");
 签名和过期时间分别为
 signature=obj.signature;
 expires= obj.expires;
 
3.最后组装url调用接口(例子):
var requestUrl    = "https://api.61topbaby.com/topbaby/restful/bss/qrCode/getBsPicture?accessKeyId=topbabykeyid&signature=" + obj.signature + "&expires=" + obj.expires;
其中api.61topbaby.com为服务器域名地址.
注意:url中accessKeyId,expires,signature三个参数x必须非空!
```


## 二、java客服端调用

* 必须导入jar包:

> topbaby-signature-1.0-SNAPSHOT.jar

* 调用方法:

> Signature.generatureSignature(String type, String data,String key)

* 返回类型:

> Map<String,Object>
签名 字段:signature
过期时间 字段:expired

* 参数说明:

>
|**名称** | 类型 |描述|例如|
|:-----------|:------------|:------------|:------------|
|type|String|请求方法|GET,POST,PUT等,必须大写
|data|String|需要加密的内容|/topbaby/restful/bss/qrCode/getBsPicture
|key|String|淘璞帮分配的密钥|topbabysecret

* 用法示例:

```
1.先导入jar包
import com.topbaby.rest.Signature;//topbaby-signature-1.0-SNAPSHOT.jar
import org.springframework.web.client.RestTemplate;//spring框架自带

2.获取签名和过期时间:
Map<String,Object> info=Signature.generatureSignature("GET", "/topbaby/restful/bss/qrCode/getBsPicture","topbabysecret");
获取签名和过期时间的方法为:
String signature =  (String) info.get("signature");
long expired=(long) info.get("expired");

3.生成的url为:
String url="https://api.61topbaby.com/topbaby/restful/bss/qrCode/getBsPicture?accessKeyId=topbabykeyid&signature="+signature+"&expires="+expires;

4.调用接口		
Object  result = restTemplate.getForObject(url, Object.class);
log.info("result="+result.toString());

```

## 三、接口调用补充说明

* 得到上面签名signature后,才可调用接口:

> 也就是说接口url中accessKeyId,expires,signature三个参数x必须非空!
其中签名字段和过期时间由上面生成

* 例如:

```
https://api.61topbaby.com/topbaby/restful/bss/qrCode/getBsPicture?accessKeyId=topbabykeyid&signature=YTVhZDQyNGVhNWFmMjU4YTZmNzlkZmRhYzA4OGUwZjgxNmM0MTk4Ng==&expires=1483929307
其中api.61topbaby.com为服务器域名地址.
特别注意:上面url中signature和expires都是由js或者java客户端获得!
```

* 成功返回(示例):

```
{result=true, message=success, brandshopId=null, brandshopName=MQD上海五角场店, imageUrl=http://www.61topbaby.com/everbss/brandshop/image/20160416/1dea2c5f-bac0-41a8-8c42-7c15e8a4eac6.jpg, qrCodeUrl=http://www.61topbaby.com/evercos/index.html?sourceId=806}
```

* 失败返回(示例):

```
{"code":400,"message":"请求过期"}
```
