package esempio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServlet() {
        // TODO Auto-generated constructor stub
    }

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Francesco Served at: ").append(request.getContextPath());

		String risposta="Questa e' la lista dei messaggi 10:\n";
		
		//metodo query
		try {
			risposta +=query();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//scrive su browser
		response.getWriter().append(risposta);

	}

	/**
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private String query() throws ClassNotFoundException, SQLException {
		String risposta="";
		String driver = "org.mariadb.jdbc.Driver";
		Class.forName(driver);
		System.out.println("classe " + driver + " e' nel cp");

		// stringa di connessione
		String url = "jdbc:mariadb://centauri.proximainformatica.com:193/academyfs07";
		// connessione con username e password
		Connection con = DriverManager.getConnection(url, "acfs07", "acfs07");

		System.out.println("sono riuscito a connettermi al database urs: " + url);

		Statement cmd = con.createStatement();
		String query = "SELECT * FROM messages;";
		ResultSet res = cmd.executeQuery(query);

		while (res.next()) {
			risposta+= res.getString("id");
			risposta+="\t-\t";
			risposta+=res.getString("userName");
			risposta+="\t\t-\t";
			risposta+= res.getString("textMessage");
			risposta+="\t\t-\t";
			risposta+=res.getString("userInsertedTime");
			risposta+="\t-\t";
			risposta+=res.getString("serverReceivedTime");
			
//			risposta+=res.getString(1);
//			risposta+=" - ";
//			risposta+=res.getString(2);
			risposta+="\n";
		}
		
		res.close();
		cmd.close();
		return risposta;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
