package org.example.portfolioComponents.asset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="assets")
@Getter @Setter @NoArgsConstructor
public class Asset implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private int id;

    private String name, type;
    private float marketPrice, amount, value;

    public Asset(String type, String name, float amount, float price) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.marketPrice = price;
        this.value = amount*marketPrice;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", marketPrice=" + marketPrice +
                ", amount=" + amount +
                '}';
    }
    public float addAmount(float amount, float price) {
        this.marketPrice = price;
        this.amount += amount;
        return amount;
    }

    public float removeAmount(float amount, float price) {
        this.marketPrice = price;
        this.amount -= amount;
        return amount;
    }
}
