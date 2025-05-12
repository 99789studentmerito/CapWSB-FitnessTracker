package pl.wsb.fitnesstracker.user.api;

import pl.wsb.fitnesstracker.exception.api.NotFoundException;

public class EmailNotFoundException extends NotFoundException {
  public EmailNotFoundException(String message) {
    super(message);
  }


}
