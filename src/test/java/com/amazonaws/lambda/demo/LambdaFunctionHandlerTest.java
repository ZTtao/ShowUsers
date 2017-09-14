package com.amazonaws.lambda.demo;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.demo.dto.RequestModel;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static RequestModel input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
        Context ctx = createContext();
        input = new RequestModel();
//        input.setToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MDUyMjYyMDEsInN1YiI6IntcInVzZXJOYW1lXCI6XCJhZG1pblwiLFwidXNlcklkXCI6NH0iLCJleHAiOjE1MDUyMjk4MDF9.bMpO9Lh1_GHlPAmG3hFphYd2jsJdfklEQrp0oliQxL4");
        input.setLimit(1);
        input.setOffset(null);
        input.setOrder("desc");
        input.setOrderBy("user_id");
        input.setSearch("a");
        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
    }
}
