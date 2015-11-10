package org.smurve.hsr2014.service;

import org.springframework.security.core.AuthenticationException;

class AccountNotActiveException extends AuthenticationException {

  private static final long serialVersionUID = 8463876146625956541L;

  public AccountNotActiveException(String msg) {
    super(msg);
  }

}
