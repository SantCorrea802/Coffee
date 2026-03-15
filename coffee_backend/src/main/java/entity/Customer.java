package entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="firstName", nullable = false, length = 20)
    private String firstName;

    @Column(name="secondName", nullable = false, length = 20)
    private String secondName;

    @Column(name="accountNumber", nullable = false)
    private String accountNumber;

    @Column(name="credits", nullable = false)
    private Long credits;

    @Column(name="balance", nullable = false)
    private BigDecimal balance;

    @JsonCreator
    public Customer(@JsonProperty("firstName") String firstName, @JsonProperty("secondName") String secondName,
                    @JsonProperty("credits") Long credits, @JsonProperty("balance") BigDecimal balance){
        this.firstName = firstName;
        this.secondName = secondName;
        this.credits = credits;
        this.balance = balance;

    }

}
