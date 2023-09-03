package hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date creationdate;
    @OneToMany(mappedBy = "company")
    private List<Model> models;
    public Company(String name, Date creationdate) {
        this.name = name;
        this.creationdate = creationdate;
        this.models = new ArrayList<>();
    }
    public Company(int companyId) {
        id = companyId;
    }
}
