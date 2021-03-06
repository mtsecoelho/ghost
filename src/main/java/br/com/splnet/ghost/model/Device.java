package br.com.splnet.ghost.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Device {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deviceId;
	
	@NotNull(message="Ip Obrigatório")
	@NotEmpty(message="Ip Obrigatório")
	@Column(unique = true)
	private String ip;
	
	@NotNull(message="Porta Obrigatória")
	private Integer port;
	
	@NotNull(message="Descrição Obrigatória")
	@NotEmpty(message="Descrição Obrigatória")
	private String description;

}
