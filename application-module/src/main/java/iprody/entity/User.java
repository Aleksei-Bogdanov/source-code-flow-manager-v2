package iprody.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "usr")
public class User {
    @Id
    private Long id;
    private String name;
}
