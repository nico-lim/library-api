package com.myrental.web.dto;

import com.myrental.web.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudentDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static StudentDTO of(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }
}
