package note.rest.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private Error error;
    private String token;

    public enum Error {
        ok,
        invalidCredentials
    }

    public static AuthResponse success(String token) {
        return builder().error(Error.ok).token(token).build();
    }

    public static AuthResponse failed(Error error) {
        return builder().error(error).token(null).build();
    }
}
