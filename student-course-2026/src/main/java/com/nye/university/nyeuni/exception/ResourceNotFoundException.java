package com.nye.university.nyeuni.exception;

/** Thrown when a requested entity cannot be found. */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
