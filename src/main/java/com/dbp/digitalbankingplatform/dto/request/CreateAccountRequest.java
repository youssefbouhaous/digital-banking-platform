package com.dbp.digitalbankingplatform.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 * CreateAccountRequest
 *
 * @author Youssef Bouhaous
 * @since 9/27/26
 */
public record CreateAccountRequest(@NotBlank @Size(max=30) String userName,
                                   @NotBlank @Size(max=30) String firstName,
                                   @NotBlank @Size(max=30) String lastName) {
}
