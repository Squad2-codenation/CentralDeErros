package br.com.codenation.dtos;

import java.util.UUID;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {

    private UUID id;

    @NotNull
    @Size(min = 10)
    private String title;

    @NotNull
    private String details;

    @NotNull
    private UUID applicationId;

    private String applicationName;

    @NotNull
    private UUID userId;

    private String userName;

    @NotNull
    private String environment;

    @NotNull
    private String level;

    private Boolean archived;
    private Long events;
    private String createdAt;
    private String updatedAt;
}
