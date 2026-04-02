package finance_dasboard.dto;

import finance_dasboard.entity.RoleBasedAccess;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {
    
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank
    private String email;

    @Size(min = 3, message="Password must be at least 3 characters")
    private String password;
    private RoleBasedAccess role; 
}
