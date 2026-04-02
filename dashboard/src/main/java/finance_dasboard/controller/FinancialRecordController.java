package finance_dasboard.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance_dasboard.dto.DashboardSummaryDTO;
import finance_dasboard.dto.FinancialRecordRequestDTO;
import finance_dasboard.dto.FinancialRecordResponseDTO;
import finance_dasboard.entity.RecordType;
import finance_dasboard.entity.RoleBasedAccess;
import finance_dasboard.service.FinancialRecordService;
import finance_dasboard.util.RoleChecker;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {
    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }

    @PostMapping
    public FinancialRecordResponseDTO create(@Valid @RequestBody FinancialRecordRequestDTO dto, @RequestHeader("role") RoleBasedAccess role) {

        RoleChecker.checkAdmin(role);

        return service.createRecord(dto);
    }


    @GetMapping
    public Page<FinancialRecordResponseDTO> getAll( Pageable pageable,@RequestHeader("role") RoleBasedAccess role){

        RoleChecker.checkAnalystOrAdmin(role);
        return service.getAllRecords(pageable);
    }


    @PutMapping("/{id}")
    public FinancialRecordResponseDTO update(@PathVariable Long id, @RequestBody FinancialRecordRequestDTO dto, @RequestHeader("role") RoleBasedAccess role) {

        RoleChecker.checkAdmin(role);

        return service.updateRecord(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("role") RoleBasedAccess role) {

        RoleChecker.checkAdmin(role);

        service.deleteRecord(id);
    }

    @GetMapping("/type")
    public List<FinancialRecordResponseDTO> byType(@RequestParam RecordType type, @RequestHeader("role") RoleBasedAccess role) {

        RoleChecker.checkAnalystOrAdmin(role);

        return service.getByType(type);
    }

    @GetMapping("/category")
    public List<FinancialRecordResponseDTO> byCategory(@RequestParam String category,@RequestHeader("role") RoleBasedAccess role) {

        RoleChecker.checkAnalystOrAdmin(role);

        return service.getByCategory(category);
    }

    @GetMapping("/date-range")
    public List<FinancialRecordResponseDTO> byDate(@RequestParam LocalDate start,@RequestParam LocalDate end, @RequestHeader("role") RoleBasedAccess role) {

        RoleChecker.checkAnalystOrAdmin(role);

        return service.getByDateRange(start, end);
    }



    @GetMapping("/dashboard")
    public DashboardSummaryDTO dashboard(@RequestHeader("role") RoleBasedAccess role){
        return service.getDashboardSummary();
    }


    @GetMapping("/dashboard/category")
    public Map<String, Double> categorySummary(@RequestHeader("role") RoleBasedAccess role){
        return service.getCategorySummary();
    }


    @GetMapping("/dashboard/recent")
    public List<FinancialRecordResponseDTO> recent(@RequestHeader("role") RoleBasedAccess role){
        return service.getRecentRecords();
    }


    @GetMapping("/dashboard/trends")
    public Map<Integer, Double> trends(@RequestHeader("role") RoleBasedAccess role){
        return service.getMonthlyTrends();
    }
}
