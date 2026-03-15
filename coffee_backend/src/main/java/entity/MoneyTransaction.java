package entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="moneyTransaction")
public class MoneyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;







}
