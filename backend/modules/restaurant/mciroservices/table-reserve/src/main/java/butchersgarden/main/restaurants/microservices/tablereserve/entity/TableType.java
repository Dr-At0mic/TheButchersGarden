package butchersgarden.main.restaurants.microservices.tablereserve.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@jakarta.persistence.Table(name = "table_types")
public class TableType implements Serializable {
    @Serial
    private static final long serialVersionUID = 1487476613690790609L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableTypeId;
    @NotBlank(message = "tableTypeName is mandatory")
    @Column(unique = true)
    private String tableTypeName;
    @NotBlank(message = "seats is mandatory")
    @Column
    private int seats;

}
