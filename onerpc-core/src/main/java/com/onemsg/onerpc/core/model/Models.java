package com.onemsg.onerpc.core.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据模型序列化工具类
 * 
 * @author mashuguang
 * @since 2021
 */
public class Models {

    private static final Logger log = LoggerFactory.getLogger(Models.class);

    private static final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

    private Models() {}

    /**
     * 请求模型转化为比特数组, 转化失败返回 null
     * @param requestModel
     * @return
     */
    public static byte[] toBytes(RequestModel requestModel) {
        try {
            return objectMapper.writeValueAsBytes(requestModel);
        } catch (JsonProcessingException e) {
           log.warn("requestModel convert to bytes failed, {}", e.getMessage());
        }
        return null;
    }

    public static byte[] toBytes(ResponseModel responseModel) {
        try {
            return objectMapper.writeValueAsBytes(responseModel);
        } catch (JsonProcessingException e) {
            log.warn("responseModel convert to bytes failed, {}", e.getMessage());
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
            log.warn("bytes convert to RequestModel failed, {}", e.getMessage());
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
            log.warn("bytes convert to ResponseModel failed, {}", e.getMessage());
        }
        return null;
    }
}
