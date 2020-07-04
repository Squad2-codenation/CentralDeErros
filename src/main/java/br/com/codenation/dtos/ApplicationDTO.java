package br.com.codenation.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

	private UUID id;
	private String name;
	private String token;
	private String createdAt;
	private String updatedAt;

}
