package br.com.fatec;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    @NotNull(message = "O nome não pode ser nulo")
    @NotEmpty(message = "O nome não pode ser vazio")
    private String nome;
    
    @NotNull(message = "A descrição não pode ser nulo")
    @NotEmpty(message = "A descrição não pode ser vazio")
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo")
    private Double preco;    
}
