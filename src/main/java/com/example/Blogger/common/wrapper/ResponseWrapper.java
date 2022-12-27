package com.example.Blogger.common.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {

    @JsonProperty("path")
    private String path;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private Object result;

    @JsonProperty("errors")
    private Object errors;


    public static ResponseWrapper successResponse(String path, String message, Object result){
        return new ResponseWrapper(path, "success", message, result, null);
    }

    public static ResponseWrapper errorResponse(String path, String message, Object errors){
        return new ResponseWrapper(path, "error", message, null, errors);
    }

}
