package exam.model.dtos;

import exam.model.WarrantyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaptopImportDto {

    @Size(min = 8)
    @NotNull
    private String macAddress;

    @Positive
    @NotNull
    private double cpuSpeed;

    @Min(8)
    @Max(128)
    @NotNull
    private int ram;

    @Min(128)
    @Max(1024)
    @NotNull
    private int storage;

    @Size(min = 10)
    @NotNull
    private String description;

    @Positive
    @NotNull
    private double price;

    @NotNull
    private WarrantyType warrantyType;

    @NotNull
    private NameDto shop;

}
