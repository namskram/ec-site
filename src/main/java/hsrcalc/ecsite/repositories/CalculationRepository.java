package hsrcalc.ecsite.repositories;

import hsrcalc.ecsite.domain.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    // Custom queries can be added here later if needed
}