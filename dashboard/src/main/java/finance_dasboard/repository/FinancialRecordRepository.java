package finance_dasboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import finance_dasboard.entity.FinancialRecord;
import finance_dasboard.entity.RecordType;

import java.util.List;
import java.time.LocalDate;



public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>{

    List<FinancialRecord> findByType(RecordType type);
    

    List<FinancialRecord> findByCategory(String category);

    List<FinancialRecord> findByDateBetween(LocalDate start, LocalDate end);


    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = 'INCOME'")
    Double getTotalIncome();


    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = 'EXPENSE'")
    Double getTotalExpense();


    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f GROUP BY f.category")
    List<Object[]> getCategoryTotals();

    List<FinancialRecord> findTop5ByOrderByDateDesc();


    @Query("SELECT MONTH(f.date), SUM(f.amount) FROM FinancialRecord f GROUP BY MONTH(f.date)")
    List<Object[]> getMonthlyTrends();


    Page<FinancialRecord> findAll(Pageable pageable);
}
