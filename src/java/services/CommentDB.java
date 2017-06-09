package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Comment;

public class CommentDB {

  private conMysql con;

  private String sPosts = "select id, idPost, user, message, date, status from comments where idPost =  ? and status='A' order by date desc";
  private String iPost = "INSERT INTO comments(idPost,user,message,date) VALUES (?,?,?,sysdate())";

  public CommentDB() {
    con = new conMysql();
  }

  public ArrayList getComments(int id) throws ClassNotFoundException, SQLException {
    ArrayList<Comment> aComments = new ArrayList<>();
    Comment c;
    PreparedStatement ps;
    ResultSet rs;

    con.conect();
    ps = con.prepareStatement(sPosts);
    ps.setInt(1, id);
    rs = ps.executeQuery();

    while (rs.next()) {
      c = new Comment(
              rs.getInt("id"),
              rs.getInt("idPost"),
              rs.getString("user"),
              rs.getString("message"),
              rs.getTimestamp("date"),
              rs.getString("status")
      );
      aComments.add(c);
    }

    rs.close();
    con.close();
    return aComments;
  }
  
  
  public int insertComment(Comment c) throws ClassNotFoundException, SQLException {
    int totalReg = -1;
    PreparedStatement ps;

    try {
      con.conect();
      con.autoCommit(false);

      ps = con.prepareStatement(iPost);
      ps.setInt(1, c.getIdPost());
      ps.setString(2, c.getUser());
      ps.setString(3, c.getMessage());
      totalReg = ps.executeUpdate();

      con.Commit();
      con.close();
    } catch (SQLException ex) {
      con.Rollback();
      con.close();
      ex.printStackTrace();
    }
    return totalReg;
  }

}
