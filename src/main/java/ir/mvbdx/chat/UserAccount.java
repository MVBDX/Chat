package ir.mvbdx.chat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class UserAccount {
    private final String username;
    private final String password;
}
