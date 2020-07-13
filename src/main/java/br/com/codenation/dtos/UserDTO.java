package br.com.codenation.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private UUID id;

	@NotNull
	@Size(min = 3)
	private String name;

	@Email
	@NotNull()
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String token;
	private Boolean active;
	private String createdAt;
	private String updatedAt;

}
