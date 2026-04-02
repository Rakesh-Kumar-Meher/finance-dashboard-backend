package finance_dasboard.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import finance_dasboard.dto.DashboardSummaryDTO;
import finance_dasboard.dto.FinancialRecordRequestDTO;
import finance_dasboard.dto.FinancialRecordResponseDTO;
import finance_dasboard.entity.FinancialRecord;
import finance_dasboard.entity.RecordType;
import finance_dasboard.repository.FinancialRecordRepository;

@Service
public class FinancialRecordService {
    
    private final FinancialRecordRepository repository;


    public FinancialRecordService(FinancialRecordRepository repository){
        this.repository = repository;
    }


    public FinancialRecordResponseDTO createRecord(FinancialRecordRequestDTO dto){
        FinancialRecord record = new FinancialRecord();

        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setDescription(dto.getDescription());

        return mapToDTO(repository.save(record));
    }

    public Page<FinancialRecordResponseDTO> getAllRecords(Pageable pageable){
        return repository.findAll(pageable).map(this::mapToDTO);
    }

    public FinancialRecordResponseDTO updateRecord(Long id, FinancialRecordRequestDTO dto){
        FinancialRecord record = repository.findById(id).orElseThrow(()-> new RuntimeException("Record not found"));

        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setDescription(dto.getDescription());

        return mapToDTO(repository.save(record));
    }


    public void deleteRecord(Long id) {
        FinancialRecord record = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));

        repository.delete(record);
    }

    public List<FinancialRecordResponseDTO> getByType(RecordType type) {
        return repository.findByType(type)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public List<FinancialRecordResponseDTO> getByCategory(String category) {
        return repository.findByCategory(category)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<FinancialRecordResponseDTO> getByDateRange(LocalDate start, LocalDate end) {
        return repository.findByDateBetween(start, end)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }




    public DashboardSummaryDTO getDashboardSummary(){
        Double income = repository.getTotalIncome();
        Double expense = repository.getTotalExpense();

        if(income == null) income = 0.0;
        if(expense == null) expense = 0.0;

        DashboardSummaryDTO dto = new DashboardSummaryDTO();
        dto.setTotalIncome(income);
        dto.setTotalExpense(expense);
        dto.setNetBalance(income - expense);

        return dto;
    }


    public Map<String, Double> getCategorySummary(){

        List<Object[]> results = repository.getCategoryTotals();

        Map<String, Double> map = new HashMap<>();

        for(Object[] row : results){
            String category = (String) row[0];
            Double total = (Double) row[1];

            map.put(category, total);
        }

        return map;
    }


    public List<FinancialRecordResponseDTO> getRecentRecords(){
        return repository.findTop5ByOrderByDateDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public Map<Integer, Double> getMonthlyTrends(){

        List<Object[]> results = repository.getMonthlyTrends();

        Map<Integer, Double> map = new HashMap<>();

        for(Object[] row : results){
            Integer month = (Integer) row[0];
            Double total = (Double) row[1];

            map.put(month, total);
        }

        return map;
    }



    private FinancialRecordResponseDTO mapToDTO(FinancialRecord record){

        FinancialRecordResponseDTO dto = new FinancialRecordResponseDTO();

        dto.setId(record.getId());
        dto.setAmount(record.getAmount());
        dto.setType(record.getType());
        dto.setCategory(record.getCategory());
        dto.setDate(record.getDate());
        dto.setDescription(record.getDescription());

        return dto;
    }
}
