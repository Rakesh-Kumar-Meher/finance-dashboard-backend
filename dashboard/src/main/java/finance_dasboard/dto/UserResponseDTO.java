package finance_dasboard.dto;

import java.time.LocalDateTime;

import finance_dasboard.entity.RoleBasedAccess;
import lombok.Data;

@Data
public class UserResponseDTO {
    

    private Long id;
    private String name;
    private String email;
    private RoleBasedAccess role;
    private boolean active;
    private LocalDateTime createdAt;
}
