package hsrcalc.ecsite.repositories;

import hsrcalc.ecsite.domain.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    // Add custom queries here later if needed
}