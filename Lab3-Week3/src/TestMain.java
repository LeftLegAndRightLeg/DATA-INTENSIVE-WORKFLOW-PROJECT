
import javax.jws.WebService;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import net.xqj.sedna.SednaXQDataSource;
import javax.jws.WebMethod;
import javax.jws.WebParam;

// WSDL URL : platform:/resource/Lab3-Week3/WebContent/wsdl/TestMain.wsdl

@WebService()
public class TestMain {
	TestQuery test;
	XQDataSource xqs;
	XQConnection conn;
	public TestMain() throws Exception{
		System.out.println(" HERE I AM !!!!");
		test = new TestQuery();
		xqs = new SednaXQDataSource();
	    xqs.setProperty("serverName", "localhost");
	    xqs.setProperty("databaseName", "mydblp");

	    conn = xqs.getConnection("SYSTEM", "MANAGER");
	    System.out.println("Connected to Sedna.");
	}
	
	@WebMethod(action = "weatherCoAuthor")
	public String weatherCoAuthor(@WebParam(name = "author1") String author1, @WebParam(name = "author2") String author2) throws XQException {
		return test.whetherCoAuthor(conn, author1, author2);
	}

	@WebMethod(action = "getPaperByTitle")
	public String getPaperByTitle(@WebParam(name = "title") String title) throws XQException {
		return test.findByTitle(conn, title);
	}
	@WebMethod(action = "getAllPublications")
	public String getAllPublications(@WebParam(name = "authorName") String authorName) throws XQException {
		return test.findByAuthor(conn, authorName);
	}
	@WebMethod(action = "searchKeyWord")
	public String searchKeyWord(@WebParam(name = "keyword") String keyword) throws XQException {
		return test.findByKeyWord(conn, keyword);	
		}
	@WebMethod(action = "getCoAuthor")
	public String getCoAuthor(@WebParam(name = "authorName") String authorName) throws XQException {
		return test.listCoAuthor(conn, authorName);
	}
}