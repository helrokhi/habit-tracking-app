package ru.ylab.dto;

import lombok.*;
import ru.ylab.dto.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthDto {
    private Long id;
    private String email;
    private String password;
    private Role role;
}
