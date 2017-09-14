package com.amazonaws.lambda.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.lambda.demo.dto.RequestModel;
import com.amazonaws.lambda.demo.dto.User;
import com.amazonaws.lambda.demo.service.IUserService;
import com.amazonaws.lambda.demo.service.impl.UserServiceImpl;
import com.amazonaws.lambda.demo.util.BaseInfoRecorder;
import com.amazonaws.lambda.demo.util.MyStringUtil;
import com.amazonaws.lambda.demo.util.TokenUtil;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<RequestModel, String> {

	@Override
	public String handleRequest(RequestModel input, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("[" + new Date() + "]Input: " + JSONObject.toJSONString(input));
		String token = input.getBaseInfo().getToken();
		logger.log("[" + new Date() + "]token: " + token);
		User user = TokenUtil.validateToken(token);
		Map<String, Object> map = new HashMap<>();
		if (user == null) {
			map.put("success", false);
			map.put("message", "token is wrong.");
		} else {
			String orderBy = input.getOrderBy();
			Integer offset = input.getOffset();
			Integer limit = input.getLimit();
			String order = input.getOrder();
			String search = input.getSearch();
			if (orderBy != null) {
				orderBy = MyStringUtil.camelToUnderLine(orderBy);
				orderBy = orderBy + " " + order;
			}
			if (offset == null)
				offset = 0;
			if (limit == null)
				limit = 0;
			IUserService userService = new UserServiceImpl();
			List<User> list = new ArrayList<>();
			int count = 0;
			try {
				list = userService.getUserByOrderbyLimit(context, orderBy, offset, limit, search);
				count = userService.getUserCount(context, search);
				map.put("success", true);
				map.put("rows", list);
				map.put("total", count);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", false);
				map.put("message", "exception.");
			}
		}
		logger.log("[" + new Date() + "]showUsers:" + map);
		// 访问信息记录
		BaseInfoRecorder.record(user == null ? "unknow" : user.getUserId().toString(), input.getBaseInfo(),
				"[" + new Date() + "]showUsers:" + JSONObject.toJSONString(map));
		return JSONObject.toJSONString(map);
	}

}
