package pl.wsb.fitnesstracker.user.internal;

import jakarta.annotation.Nullable;

public record UserEmailDto(@Nullable Long Id, String email) {
}
