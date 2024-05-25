package butchersgarden.main.restaurants.microservices.tablereserve.repository;

import butchersgarden.main.restaurants.microservices.tablereserve.entity.TableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableTypeRepository extends JpaRepository<TableType, Long> {

}
