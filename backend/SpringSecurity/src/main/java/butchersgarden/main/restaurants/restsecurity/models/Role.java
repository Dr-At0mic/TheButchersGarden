package butchersgarden.main.restaurants.restsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Long roleId;
    private String roleName;
    private List<Functions> functionsList ;
}
