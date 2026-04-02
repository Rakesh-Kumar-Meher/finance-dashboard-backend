package finance_dasboard.dto;

import java.time.LocalDate;

import finance_dasboard.entity.RecordType;
import lombok.Data;

@Data
public class FinancialRecordResponseDTO {
    
    private Long id;
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String description;
}
