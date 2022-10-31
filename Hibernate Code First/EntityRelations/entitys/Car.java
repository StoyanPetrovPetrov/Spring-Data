package entitys;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends Vehicle{
    private static final String CAR_TYPE = "CAR";
    @Basic
    private int seats;
    public Car(){
        super(CAR_TYPE);
    }
    public Car(String model,String fuelType,int seats){
        this();

        this.model = model;
        this.fuelType = fuelType;
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
