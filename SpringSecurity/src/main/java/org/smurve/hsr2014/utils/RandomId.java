package org.smurve.hsr2014.utils;

import org.springframework.security.crypto.codec.Base64;

import java.util.Random;

/**
 * based on base64, provides url-safe random strings
 */
public class RandomId {

  Random rnd = new Random();

  public static String nextId() {
    byte[] bytes = new byte[16];
    new Random().nextBytes(bytes);
    String id = new String(Base64.encode(bytes));
    id = id.replace('/', '_');
    id = id.replace("=", "");
    return id;
  }

}
