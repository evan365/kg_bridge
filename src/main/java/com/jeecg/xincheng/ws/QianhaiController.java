package com.jeecg.xincheng.ws;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jeecgframework.web.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 前海保理系统的接口中转服务
 */
@Api(value = "qianhai", description = "前海保理系统中转接口服务", tags = "前海保理API")
@Controller
@RequestMapping("/qianhai")
public class QianhaiController {
	private static final Logger logger = LoggerFactory.getLogger(QianhaiController.class);
	@Autowired
	private UserService userService;

	@ApiOperation(value = "获取TOKEN")
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> token(@RequestParam String appHost, @RequestParam String appKey, @RequestParam String appSecret) {
		logger.info("前海保理Token[{}][{}][{}]", new Object[] { appHost, appKey, appSecret });

		String token = null;
		try {
			token = getToken(appHost, appKey, appSecret);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity(token, HttpStatus.OK);
	}

	private String getToken(String appHost, String appKey, String appSecret) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = null;

		try {

			post = new HttpPost(appHost + "/api/auth/access/token");

			// 设置http请求头的信息，目前仅限定了请求协议为json，编码为utf-8
			// 构造消息头
			post.setHeader("Content-type", "application/json; charset=utf-8");
			post.setHeader("Connection", "Close");

			/**
			 * 构造请求的json参数，可以通过fastjson、gson等框架来构造该字符串
			 */
			String requestParameter = "{appKey:\"" + appKey + "\",appSecret:\"" + appSecret + "\"}";

			// 构建消息实体
			StringEntity entity = new StringEntity(requestParameter, "UTF-8");
			entity.setContentEncoding("UTF-8");
			// 发送Json格式的数据请求
			entity.setContentType("application/json");
			post.setEntity(entity);

			HttpResponse response = null;
			response = httpClient.execute(post);
			// 检验返回码
			int statusCode = response.getStatusLine().getStatusCode();
			if (org.apache.commons.httpclient.HttpStatus.SC_OK != statusCode) {
				throw new RuntimeException("获取token的HTTP请求响应失败，响应代码：" + statusCode);
			}
			HttpEntity resultEntity = response.getEntity();
			if (resultEntity != null) {
				// TODO 以下为解析响应的json结构，可按需优化为定义一个POJO对象，并直接从json转换为json对象
				String resultString = EntityUtils.toString(resultEntity, "UTF-8");
				JSONObject resultJsonObject = JSONObject.parseObject(resultString);
				if (!resultJsonObject.containsKey("data")) {
					throw new RuntimeException("响应数据结构有误,响应数据为：" + resultString);
				}
				JSONObject dataJsonObject = resultJsonObject.getJSONObject("data");
				if (!dataJsonObject.containsKey("token")) {
					throw new RuntimeException("响应数据结构有误(未包含token),响应数据为：" + resultString);
				}
				return dataJsonObject.getString("token");
			}
		} catch (IOException e) {
			// TODO 按需处理异常
			e.printStackTrace();
			logger.error("获取token出错，这里需要按照业务需要实现自己的错误处理逻辑", e);
		}

		return null;
	}

	@ApiOperation(value = "生成保理单")
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> newApply(@RequestParam String appHost, @RequestParam String appKey, @RequestParam String appSecret, @RequestParam String json) {
		logger.info("生成保理单[appHost:{}][json:{}]", new Object[] { appHost, json });

		String token = null;
		try {
			token = getToken(appHost, appKey, appSecret);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		if (token == null) {
			return new ResponseEntity("获取Token出错", HttpStatus.UNAUTHORIZED);
		}

		// TODO 使用的post请求，如果是GET请求，需使用HttpGet，PUT使用HttpPut，DELETE使用HttpDelete
		HttpPost post = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			post = new HttpPost(appHost + "/api/kingdee/payment/info");
			// 构造消息头
			post.setHeader("Content-type", "application/json; charset=utf-8");
			post.setHeader("Connection", "Close");
			post.setHeader("token", token);

			// 对请求参数进行加密
			String rsaParameter = rsaEncryptedString(json, appSecret);

			// 构建消息实体
			StringEntity entity = new StringEntity(rsaParameter, "UTF-8");
			entity.setContentEncoding("UTF-8");
			// 发送Json格式的数据请求
			entity.setContentType("application/json");
			post.setEntity(entity);

			HttpResponse response = httpClient.execute(post);

			// 检验返回码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != org.apache.commons.httpclient.HttpStatus.SC_OK) {
				logger.error("请求出错: " + statusCode);
			}

			HttpEntity resultEntity = response.getEntity();
			if (resultEntity != null) {
				String resultString = EntityUtils.toString(resultEntity, "UTF-8");
				JSONObject resultJsonObject = JSON.parseObject(resultString);
				if (!resultJsonObject.containsKey("errcode")) {
					return new ResponseEntity("保理发送接口响应数据结构有误,响应数据为：" + resultString, HttpStatus.BAD_REQUEST);
				}
				String errcode = resultJsonObject.getString("errcode");
				String errmsg = resultJsonObject.getString("errmsg");
				if (!"0".equals(errcode) && !"200".equals(errcode)) {
					return new ResponseEntity(MessageFormat.format("保理接口返回失败[{0}][{1}]", errcode, errmsg), HttpStatus.BAD_REQUEST);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("保理发送接口问题" + e.getMessage(), HttpStatus.BAD_REQUEST);
		} finally {
			if (post != null) {
				try {
					// post.releaseConnection();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		return new ResponseEntity("处理完成", HttpStatus.OK);
	}

	@ApiOperation(value = "取消保理单")
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> cancelApply(@RequestParam String appHost, @RequestParam String appKey, @RequestParam String appSecret, @RequestParam String json) {
		logger.info("取消保理单[appHost:{}][json:{}]]", new Object[] { appHost, json });
		String token = null;
		try {
			token = getToken(appHost, appKey, appSecret);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

		// TODO 使用的post请求，如果是GET请求，需使用HttpGet，PUT使用HttpPut，DELETE使用HttpDelete
		HttpPost post = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			post = new HttpPost(appHost + "/api/kingdee/payment/cancel");
			// 构造消息头
			post.setHeader("Content-type", "application/json; charset=utf-8");
			post.setHeader("Connection", "Close");
			post.setHeader("token", token);

			// 对请求参数进行加密
			String rsaParameter = rsaEncryptedString(json, appSecret);

			// 构建消息实体
			StringEntity entity = new StringEntity(rsaParameter, "UTF-8");
			entity.setContentEncoding("UTF-8");
			// 发送Json格式的数据请求
			entity.setContentType("application/json");
			post.setEntity(entity);

			HttpResponse response = httpClient.execute(post);

			// 检验返回码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != org.apache.commons.httpclient.HttpStatus.SC_OK) {
				logger.error("请求出错: " + statusCode);
			}

			HttpEntity resultEntity = response.getEntity();
			if (resultEntity != null) {
				String resultString = EntityUtils.toString(resultEntity, "UTF-8");
				JSONObject resultJsonObject = JSON.parseObject(resultString);
				if (!resultJsonObject.containsKey("errcode")) {
					return new ResponseEntity("取消保理接口响应数据结构有误,响应数据为：" + resultString, HttpStatus.BAD_REQUEST);
				}
				String errcode = resultJsonObject.getString("errcode");
				String errmsg = resultJsonObject.getString("errmsg");
				if (!"0".equals(errcode) && !"200".equals(errcode)) {
					return new ResponseEntity(MessageFormat.format("取消保理接口返回失败[{0}][{1}]", errcode, errmsg), HttpStatus.BAD_REQUEST);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("取消保理接口问题" + e.getMessage(), HttpStatus.BAD_REQUEST);
		} finally {
			if (post != null) {
				try {
					// post.releaseConnection();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		return new ResponseEntity("处理完成", HttpStatus.OK);
	}

	/**
	 * 加密请求参数
	 * 
	 * @param jsonData  要加密的json参数
	 * @param appSecret
	 * @return 加密后的参数字符串
	 */
	public static String rsaEncryptedString(String jsonData, String appSecret) {

		try {

			return RsaUtils.encryptedString(jsonData, appSecret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
