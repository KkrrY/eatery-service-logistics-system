package dto.dish;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DishRequest {

    private String id; //org.hibernate.id.IdentifierGenerationException: ids for this class must be manually assigned before calling save(): entity.Dishes
    @NotNull
    @Size(min=1, message="Name must be at least 5 characters long")
    private String name;

    private Double price;

    private Date createdAt = new Date();

    private String description;

    private String imageSrc;
}
