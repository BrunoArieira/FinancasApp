package com.barieira.asMinhasFinancas.model.entity;

import com.barieira.asMinhasFinancas.model.enums.StatusLancamento;
import com.barieira.asMinhasFinancas.model.enums.TipoLancamento;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "financas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Long mes;

    @Column(name = "ano")
    private Long ano;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_registo")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataRegisto;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;


}
