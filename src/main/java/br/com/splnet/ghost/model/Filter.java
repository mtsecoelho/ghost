package br.com.splnet.ghost.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Sort.Direction;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Filter {
	
	@NotNull(message="Página Obrigatória")
	private Integer page;
	
	@NotNull(message="Tamanho Obrigatório")
	private Integer size;
	
	@NotNull(message="Direção Obrigatória")
	private Direction direction;
	
	@NotEmpty(message="Propriedade Obrigatória")
	private String property;
	
	private String value;

}