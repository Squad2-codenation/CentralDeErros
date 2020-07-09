package br.com.codenation.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private UUID id;
	private String name;
	private String email;
	private String token;
	private Boolean active;
	private String createdAt;
	private String updatedAt;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

}
