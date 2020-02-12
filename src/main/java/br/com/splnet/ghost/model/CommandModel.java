package br.com.splnet.ghost.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommandModel {
	
	@NotNull(message = "Dispositivo é obrigatório")
	private Integer deviceId;
	
	@NotNull(message = "Mensagem é obrigatória")
	@NotEmpty(message = "Mensagem é obrigatória")
	private String message;
	
}
