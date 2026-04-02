package finance_dasboard.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import finance_dasboard.dto.UserRequestDTO;
import finance_dasboard.dto.UserResponseDTO;
import finance_dasboard.entity.RoleBasedAccess;
import finance_dasboard.entity.User;
import finance_dasboard.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    private UserResponseDTO mapToDTO(User user){

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }

    public UserResponseDTO createUser(UserRequestDTO dto){
        
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setActive(true);

        if(dto.getRole() == null){
            user.setRole(RoleBasedAccess.VIEWER);
        }else{
            user.setRole(dto.getRole());
        }
        User saved = userRepository.save(user);

        return mapToDTO(saved);
    }


    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public UserResponseDTO updateUserStatus(Long id, boolean active){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setActive(active);

        return mapToDTO(userRepository.save(user));
    }


    public UserResponseDTO updateUserRole(Long id, RoleBasedAccess role){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if(!user.getActive()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is inactive");
        }

        user.setRole(role);

        return mapToDTO(userRepository.save(user));
    }
}
