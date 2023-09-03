package hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    @Id
    private int id;
    private String name;
    private int length;
    private int width;
    private String body;
    @ManyToOne
    @JoinColumn(name = "companyid")
    private Company company;
}
