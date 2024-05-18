package dto.dish;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DishResponse {
    private String id;

    private String name;

    private Double price;

    private Date createdAt = new Date();

    private String description;

    private String imageSrc;
}
