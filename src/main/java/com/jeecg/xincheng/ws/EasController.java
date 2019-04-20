package com.jeecg.xincheng.ws;

import java.net.URL;
import java.util.HashMap;

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

import com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SrvProxy;
import com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SrvProxyServiceLocator;
import com.jeecg.xincheng.ws.easlogin.EASLoginProxy;
import com.jeecg.xincheng.ws.easlogin.EASLoginProxyServiceLocator;
import com.jeecg.xincheng.ws.easlogin.WSContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 控股集团金蝶中转接口服务
 */
@Api(value = "eas", description = "控股集团金蝶中转接口服务", tags = "EAS API")
@Controller
@RequestMapping("/eas")
public class EasController {
	private static final Logger logger = LoggerFactory.getLogger(EasController.class);
	@Autowired
	private UserService userService;

	@ApiOperation(value = "EAS登录获取sessionId")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> token(@RequestParam String appHost, @RequestParam String dataCenter, @RequestParam String user, @RequestParam String password) {
		logger.info("EAS登录[{}][{}][{}]", new Object[] { user, appHost, dataCenter });

		String token = null;
		try {
			token = getToken(appHost, dataCenter, user, password);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity(token, HttpStatus.OK);
	}

	private String getToken(String appHost, String dataCenter, String user, String password) throws Exception {

		EASLoginProxy proxy = new EASLoginProxyServiceLocator().getEASLogin(new URL(appHost + "/ormrpc/services/EASLogin"));
		String slnName = "eas";
		String language = "l2";
		WSContext ctx = proxy.login(user, password, slnName, dataCenter, language, 1);
		logger.info("user: {}, sessionId:{}", user, ctx.getSessionId());

		return ctx.getSessionId();
	}

	@ApiOperation(value = "引入承保单")
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importAccept(@RequestParam String appHost, @RequestParam String dataCenter, @RequestParam String user, @RequestParam String password, @RequestParam String json) {
		logger.info("引入承保单[appHost:{}][json:{}]", new Object[] { appHost, json });

		HashMap<String, String> map = new HashMap<String, String>();
		String sessionId = null;
		try {
			sessionId = getToken(appHost, dataCenter, user, password);
		} catch (Exception e) {
			map.put("code", "99");
			map.put("message", e.getMessage());
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
		if (sessionId == null) {
			map.put("code", "99");
			map.put("message", "EAS登录出错");
			return new ResponseEntity("EAS登录出错", HttpStatus.OK);
		}

		String msg = null;
		try {
			WSBaoLiFacade2SrvProxy proxy = new WSBaoLiFacade2SrvProxyServiceLocator().getWSBaoLiFacade2(new URL(appHost + "/ormrpc/services/WSBaoLiFacade2"));
			msg = proxy.importAccept(json, sessionId);
		} catch (Exception e) {
			map.put("code", "99");
			map.put("message", e.getMessage());
			return new ResponseEntity(map, HttpStatus.OK);
		}

		return new ResponseEntity(msg, HttpStatus.OK);
	}

	@ApiOperation(value = "引入放款单")
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importPayBill(@RequestParam String appHost, @RequestParam String dataCenter, @RequestParam String user, @RequestParam String password, @RequestParam String json) {
		logger.info("引入承保单[appHost:{}][json:{}]", new Object[] { appHost, json });

		HashMap<String, String> map = new HashMap<String, String>();
		String sessionId = null;
		try {
			sessionId = getToken(appHost, dataCenter, user, password);
		} catch (Exception e) {
			map.put("code", "99");
			map.put("message", e.getMessage());
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
		if (sessionId == null) {
			map.put("code", "99");
			map.put("message", "EAS登录出错");
			return new ResponseEntity("EAS登录出错", HttpStatus.OK);
		}

		String msg = null;
		try {
			WSBaoLiFacade2SrvProxy proxy = new WSBaoLiFacade2SrvProxyServiceLocator().getWSBaoLiFacade2(new URL(appHost + "/ormrpc/services/WSBaoLiFacade2"));
			msg = proxy.importPayBill(json, sessionId);
		} catch (Exception e) {
			map.put("code", "99");
			map.put("message", e.getMessage());
			return new ResponseEntity(map, HttpStatus.OK);
		}

		return new ResponseEntity(msg, HttpStatus.OK);
	}

}
