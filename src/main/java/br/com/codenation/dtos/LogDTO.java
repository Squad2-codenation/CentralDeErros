package br.com.codenation.dtos;

import java.util.UUID;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {

    private UUID id;
    private String title;
    private String details;
    private UUID applicationId;
    private String applicationName;
    private Boolean archived;
    private UUID userId;
    private String userName;
    private String level;
    private Long events;
    private String environment;
    private String createdAt;
    private String updatedAt;
}
