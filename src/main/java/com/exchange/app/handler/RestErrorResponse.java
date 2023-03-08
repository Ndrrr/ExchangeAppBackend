package com.exchange.app.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonInclude(Include.NON_NULL)
public class RestErrorResponse {

    private String code;
    private String message;
    private Map<String, String> explanations;

    public static RestErrorResponse of(String code, String message) {
        return new RestErrorResponse(code, message, Collections.emptyMap());
    }

}