package com.onemsg.onerpc.core.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            return objectMapper.readValue(bytes, RequestModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseModel toResponseModel(byte[] bytes){
        try {
            return objectMapper.readValue(bytes,  ResponseModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
