package br.com.codenation.dtos;

import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

	private UUID id;
	private String name;
	private String token;
	private String createdAt;
	private String updatedAt;

}
