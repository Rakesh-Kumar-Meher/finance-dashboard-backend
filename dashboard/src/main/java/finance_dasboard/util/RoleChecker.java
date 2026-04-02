package finance_dasboard.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import finance_dasboard.entity.RoleBasedAccess;

public class RoleChecker {
    
    public static void checkAdmin(RoleBasedAccess role){
        if(role != RoleBasedAccess.ADMIN){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: Admin only");
        }
    }    
    public static void checkAnalystOrAdmin(RoleBasedAccess role){
        if(role != RoleBasedAccess.ADMIN && role != RoleBasedAccess.ANALYST){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: Analyst/Admin only");
        }
    }
}
