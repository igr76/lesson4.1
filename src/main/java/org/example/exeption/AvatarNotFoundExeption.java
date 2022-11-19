package org.example.exeption;

public class AvatarNotFoundExeption extends RuntimeException{
  private final  long id;
  public AvatarNotFoundExeption(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }


  }
}
