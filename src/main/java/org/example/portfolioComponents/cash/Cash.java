package org.example.portfolioComponents.cash;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="cash")
@Getter
@Setter
@NoArgsConstructor
public class Cash implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public float value;
    public Cash(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Cash" +
                "value: " + value;
    }
}
