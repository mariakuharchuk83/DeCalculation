package exam.domain;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book implements Serializable {
    private String name;
    private List<String> authors;
    private String publishing;
    private Long year;
    private Long pagesQty;
    private Long price;
    private String bindingType;
}