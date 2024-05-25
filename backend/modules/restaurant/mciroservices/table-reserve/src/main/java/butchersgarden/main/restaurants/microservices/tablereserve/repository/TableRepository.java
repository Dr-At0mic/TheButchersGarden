package butchersgarden.main.restaurants.microservices.tablereserve.repository;

import butchersgarden.main.restaurants.microservices.tablereserve.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
    @Query(value = "SELECT t.* FROM tables t " +
            "INNER JOIN table_types tt ON t.table_type_id = tt.table_type_id " +
            "WHERE t.is_ac = :isAc AND tt.table_type_id = :tableTypeId LIMIT 1",
            nativeQuery = true)
    Optional<Table> findTableByIsAcAndTableType(@Param("isAc") boolean isAc, @Param("tableTypeId") Long tableTypeId);

}
