package br.com.codenation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnvironmentEnum {
	DEVELOPMENT("DEV"), HOMOLOGATION("HOM"), PRODUCTION("PRD");

	private String description;

}
