package com.amazonaws.lambda.demo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.lambda.demo.dto.BaseInfo;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class BaseInfoRecorder {
	public static void record(String userId, BaseInfo info, String message) {
		// DynamoDB full access only
		String accessKey = "AKIAIOFXZ6NXD26OSSXA";
		String secretKey = "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";
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(credentials);
		AmazonDynamoDBAsync client = AmazonDynamoDBAsyncClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2)
				.withCredentials(provider).build();
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("record");
		Long timestamp = new Date().getTime();
		Map<String, Object> map = new HashMap<>();
		map.put("baseInfo", JSONObject.toJSONString(info));
		map.put("message", message);
		table.putItem(new Item().withPrimaryKey("user_id", userId, "timestamp", timestamp).withMap("record", map));
	}
}
