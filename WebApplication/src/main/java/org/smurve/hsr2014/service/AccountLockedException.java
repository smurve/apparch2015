package org.smurve.hsr2014.service;

import org.springframework.security.core.AuthenticationException;

class AccountLockedException extends AuthenticationException {

  private static final long serialVersionUID = 3633058569958720667L;

  public AccountLockedException(String msg) {
    super(msg);
  }

}
