package com.netcracker.komarov.request.service.util;

import org.springframework.stereotype.Component;

@Component
public class ErrorJson {
    public String getErrorJson(String message) {
        return "{\"error\":\"" + message + "\"}";
    }
}
