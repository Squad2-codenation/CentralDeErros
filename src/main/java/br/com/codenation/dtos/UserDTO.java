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
public class UserDTO {
	
	private UUID id;
	private String name;
	private String email;
	private String token;
	private Boolean active;
	private String createdAt;
	private String updatedAt;
}
