package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Comment;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import services.CommentDB;

public class list extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {    
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    
    String param = request.getParameter("idPost");
    System.out.println(param);
    
    response.setContentType("application/json;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {

      ArrayList<Comment> aComments = null;
      Comment c = null;
      CommentDB db = new CommentDB();

      aComments = db.getComments(Integer.parseInt(param));

      JSONObject obj = new JSONObject();
      obj.put("error", false);
      obj.put("message", " ");

      JSONArray aC = new JSONArray();
      for (int i = 0; i < aComments.size(); i++) {
        JSONObject jsonD = new JSONObject();
        c = aComments.get(i);
        jsonD.put("id", c.getId());
        jsonD.put("idPost", c.getIdPost());
        jsonD.put("user", c.getUser());
        jsonD.put("message", c.getMessage());
        jsonD.put("date", c.getDate().toInstant());
        jsonD.put("status", c.getStatus());
        aC.add(jsonD);
      }
      obj.put("comments", aC);

      out.println(obj);
      out.flush();
      out.close();
    } catch (JSONException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      boolean error;
      String message;
      StringBuilder sb = new StringBuilder();
      BufferedReader br = request.getReader();
      String str = null;
      while ((str = br.readLine()) != null) {
        sb.append(str);
      }

      JSONObject jsObj = new JSONObject(sb.toString());
      Comment c = new Comment(
              jsObj.getInt("idPost"),
              jsObj.getString("user"),
              jsObj.getString("message")
      );

      CommentDB db = new CommentDB();
      int totalReg = db.insertComment(c);

      if (totalReg == 1) {
        error = false;
        message = "Se ingreso correctamente el comentario";
      } else {
        error = true;
        message = "Error al insertar comentario";
      }

      JSONObject obj = new JSONObject();
      obj.put("error", error);
      obj.put("message", message);

      response.setContentType("application/json; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println(obj);
      out.flush();
      out.close();

    } catch (JSONException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(list.class.getName()).log(Level.SEVERE, null, ex);
    }
          
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
