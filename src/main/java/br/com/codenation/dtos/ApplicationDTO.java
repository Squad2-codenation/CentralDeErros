package br.com.codenation.dtos;

import java.util.UUID;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

	private UUID id;

	@NotNull
	@Size(min = 3)
	private String name;
	private String token;
	private String createdAt;
	private String updatedAt;

}
