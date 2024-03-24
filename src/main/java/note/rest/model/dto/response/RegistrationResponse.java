package note.rest.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private Error error;
    private Long id;
    private String username;
    private String email;
    private String token;

    public enum Error {
        ok,
        usernameAlreadyExists,
        invalidPassword,
        emailAlreadyExists,
        failToCrateUser
    }

    public static RegistrationResponse success(Long id, String username, String email, String token) {
        return builder().error(Error.ok).id(id).username(username).email(email).token(token).build();
    }

    public static RegistrationResponse failed(Error error) {
        return builder().error(error).id(null).username(null).email(null).build();
    }
}
