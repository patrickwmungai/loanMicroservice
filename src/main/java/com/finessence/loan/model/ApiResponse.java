/*
 * Tangazoletu Limited (c) 2018.
 * 
 *  http://tangazoletu.com
 */
package com.finessence.loan.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * API Response Wrapper
 *
 * @author patrick
 * @param <T> Particular entity type
 */
@XmlRootElement
@JsonPropertyOrder(value = {"responseCode", "responseDescription", "entity"})
@XmlType(propOrder = {"responseCode", "responseDescription", "entity"})
public class ApiResponse<T> {

    protected String responseCode;
    protected String responseDescription;
    private T entity;

    public ApiResponse() {
    }

    public ApiResponse(String responseCode, String responseDescription, T entity) {
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        this.entity = entity;
    }

    ApiResponse(String responseCode, String responseDescription) {
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
    }

    /**
     *
     * @return Payload of the response.
     */
    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    /**
     *
     * @return Response code
     */
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     *
     * @return Description of the response. It is optional.
     */
    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

}
