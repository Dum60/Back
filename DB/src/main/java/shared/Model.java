package shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    private int id;
    private String name;
    private int length;
    private int width;
    private String body;
    private int companyId;
}
