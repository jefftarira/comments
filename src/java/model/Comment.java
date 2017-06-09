package model;

import java.sql.Timestamp;

public class Comment {

  public Comment(int aInt, String string, String string0, Timestamp timestamp, String string1) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public int getIdPost() {
    return idPost;
  }

  public void setIdPost(int idPost) {
    this.idPost = idPost;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  private int id;
  private int idPost;
  private String user;
  private String message;
  private Timestamp date;
  private String status;

  public Comment(int id, int idPost, String user, String message, Timestamp date, String status) {
    this.setId(id);
    this.setIdPost(idPost);
    this.setUser(user);
    this.setMessage(message);
    this.setDate(date);
    this.setStatus(status);
  }

  public Comment(int idPost, String user, String message) {
    this.setIdPost(idPost);
    this.setUser(user);
    this.setMessage(message);

  }
}
