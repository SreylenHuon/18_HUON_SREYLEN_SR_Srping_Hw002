package com.sreylen.springboot_hw002.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorUpdateRequest {
    private String instructorName;
    @Schema(defaultValue = "example@gmail.com")
    private String email;
}
