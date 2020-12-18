package com.onemsg.onerpc.core.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.msgpack.jackson.dataformat.MessagePackFactory;

public class Models {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper(MessagePackFactory.builder().build());
    }

    public static byte[] toBytes(RequestModel requestModel) {
        try {
            return objectMapper.writeValueAsBytes(requestModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] toBytes(ResponseModel responseModel) {
        try {
            return objectMapper.writeValueAsBytes(responseModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RequestModel toRequestModel(byte[] bytes) {
        try {
            JsonNode node = objectMapper.readTree(bytes);

            RequestModel requestModel = new RequestModel();
            requestModel.setClassName(node.get("className").asText());
            requestModel.setMethodName(node.get("methodName").asText());
            Class<?>[] parameterTypes = objectMapper.readValue(node.get("parameterTypes").traverse(), Class[].class);
            requestModel.setParameterTypes(parameterTypes);

            ArrayNode arrayNode = node.withArray("params");
            Object[] params = new Object[arrayNode.size()];
            for (int i = 0; i < params.length; i++) {
                params[i] = objectMapper.readValue(arrayNode.get(i).traverse(), parameterTypes[i]);
            }
            requestModel.setParams(params);
            
            return requestModel;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseModel toResponseModel(byte[] bytes){
        try {
            JsonNode node = objectMapper.readTree(bytes);
            ResponseModel responseModel = new ResponseModel();
            String type = node.get("type").asText();
            responseModel.setType(type);
            responseModel.setResult(objectMapper.readValue(node.get("result").traverse(), Class.forName(type)));
            return responseModel;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
