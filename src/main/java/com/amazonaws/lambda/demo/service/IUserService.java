package com.amazonaws.lambda.demo.service;

import java.util.List;

import com.amazonaws.lambda.demo.dto.User;
import com.amazonaws.services.lambda.runtime.Context;

public interface IUserService {
	List<User> getUserByOrderbyLimit(Context context, String orderByClause, int offset, int limit, String search)
			throws Exception;

	int getUserCount(Context context, String search) throws Exception;
}
