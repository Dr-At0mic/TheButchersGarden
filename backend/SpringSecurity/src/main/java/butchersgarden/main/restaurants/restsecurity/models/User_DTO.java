package butchersgarden.main.restaurants.restsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User_DTO {
    private List<Role> roleList;
    private boolean accountStatus;
}
