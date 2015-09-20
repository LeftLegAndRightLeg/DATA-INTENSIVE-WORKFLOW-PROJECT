
import javax.xml.xquery.*;
//import javax.xml.namespace.QName;
import net.xqj.sedna.SednaXQDataSource;

public class QuickStart
{
  public static void main(String[] args) throws XQException
  {
    XQDataSource xqs = new SednaXQDataSource();
    xqs.setProperty("serverName", "localhost");
    xqs.setProperty("databaseName", "mydblp");

    XQConnection conn = xqs.getConnection("SYSTEM", "MANAGER");
    System.out.println("Connected to Sedna.");
    
    TestQuery myTest = new TestQuery();
    
    String test = myTest.findByTitle(conn, "Parallel Integer Sorting and Simulation Amongst CRCW Models.");
    System.out.println(test);
    
    test = myTest.findByAuthor(conn, "Edward Cheng");
    System.out.println(test);
    
    test = myTest.findByKeyWord(conn, "language");
    System.out.println(test);
    
    test = myTest.listCoAuthor(conn, "Edward Cheng");
    System.out.println(test);

    test = myTest.whetherCoAuthor(conn, "Edward Cheng","Dieter Gawlick");
    System.out.println(test);
    
    
    
    
    conn.close(); 
    System.out.println("Disconnected from Sedna.");
  }
}