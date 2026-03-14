package org.jsp.allocationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppResponseDTO {

    private String statusCode;
    private String errorMessage;
    private String status;
    private Object data;
}
