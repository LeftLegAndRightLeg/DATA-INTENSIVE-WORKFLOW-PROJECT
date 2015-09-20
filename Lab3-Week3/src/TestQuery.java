import javax.xml.xquery.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashSet;

//import org.w3c.dom.Element;

import javax.xml.namespace.QName;

public class TestQuery {
	// given a title then find the publication
	public String findByTitle(XQConnection conn, String title) throws XQException{
		XQExpression xqe = conn.createExpression();
		
		xqe.bindString(new QName("my"), title, null);
		
	    String xqueryString =
	    		"declare variable $my external;"
	    	    + " for $x in doc('mydblp.xml')//article"
	    	    + " where $x/title = $my"
	    	    + " return $x";

	    	    XQResultSequence rs = xqe.executeQuery(xqueryString);
	    	    StringBuilder sb = new StringBuilder();
	    	    while(rs.next()){
	    	    	Node node = rs.getNode();
	    	    	sb.append(buildArticleFromNode(node).toString());
	    	    }
	    	    System.out.println(sb.toString());
	    	    return sb.toString();
	    	      
	}
	
	// given an author name then find all his or her publications
	public String findByAuthor(XQConnection conn, String author) throws XQException{
		XQExpression xqe = conn.createExpression();
		
		xqe.bindString(new QName("my"), author, null);
		
	    String xqueryString =
	    		"declare variable $my external;"
	    	    + " for $x in doc('mydblp.xml')//article"
	    	    + " where $x/author = $my"
	    	    + " return $x";

	    XQResultSequence rs = xqe.executeQuery(xqueryString);
	    StringBuilder sb = new StringBuilder();
	    while(rs.next()){
	    	Node node = rs.getNode();
	    	sb.append(buildArticleFromNode(node).toString());
	    }
	    System.out.println(sb.toString());
	    return sb.toString();
	}
	// given a keyWord then find all publications corresponding to the keyword
	public String findByKeyWord(XQConnection conn, String keyWord) throws XQException{
		XQExpression xqe = conn.createExpression();
		
		xqe.bindString(new QName("my"), keyWord, null);
		
	    String xqueryString =
	    		"declare variable $my external;"
	    	    + " for $x in doc('mydblp.xml')//article"
	    	    + " where $x/title[contains(., $my)]"
	    	    + " return $x";

	    	    XQResultSequence rs = xqe.executeQuery(xqueryString);

	    	    StringBuilder sb = new StringBuilder();
	    	    while(rs.next()){
	    	    	Node node = rs.getNode();
	    	    	sb.append(buildArticleFromNode(node).toString());
	    	    }
	    	    System.out.println(sb.toString());
	    	    return sb.toString();
	}
	// given an author name then list all his or her co-authors
	public String listCoAuthor(XQConnection conn, String author) throws XQException{
		XQExpression xqe = conn.createExpression();
		
		xqe.bindString(new QName("my"), author, null);
		
	    String xqueryString =
	    		"declare variable $my external;"
	    	    + " for $x in doc('mydblp.xml')//article"
	    	    + " where $x/author = $my"
	    	    + " return $x/author";

	    	    XQResultSequence rs = xqe.executeQuery(xqueryString);
	    	    HashSet<String> set = new HashSet<String>();
	    	    while(rs.next()){
	    	    	Node node = rs.getNode();
	    	    	String name = node.getLastChild().
	    	                getTextContent().trim();
	    	    	set.add(name);
	    	    }
	    	    System.out.println(set.toString());
	    	    return set.toString();
	}
	// given two authors then find out if they are co-authors
	public String whetherCoAuthor(XQConnection conn, String author1, String author2) throws XQException{
		XQExpression xqe = conn.createExpression();
		
		xqe.bindString(new QName("author1"), author1, null);
		xqe.bindString(new QName("author2"), author2, null);
		
	    String xqueryString =
	    		"declare variable $author1 external;"
	    		+ " declare variable $author2 external;"
	    	    + " for $x in doc('mydblp.xml')//article"
	    	    + " where $x/author = $author1 and $x/author = $author2"
	    	    + " return $x/title/text()";

	    	    XQResultSequence rs = xqe.executeQuery(xqueryString);
	    	    boolean res = false;
	    	    while(rs.next()){
	    	    	res = true;
	    	    }
	    	    System.out.println("Are they co-authors ?    " + res);
	    	    return "Are they co-authors ?    " + res; 
	    	    
	}
	// build Article from Node
	public Article buildArticleFromNode(Node node){
		Article emp = new Article();
		emp.mdate = node.getAttributes().
	            getNamedItem("mdate").getNodeValue();
	        
	        emp.key = node.getAttributes().
	                getNamedItem("key").getNodeValue();
		NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
          Node cNode = childNodes.item(j);
          if (cNode instanceof Element) {
            String content = cNode.getLastChild().
                getTextContent().trim();
            switch (cNode.getNodeName()) {
              case "author":
                emp.author.add(content);
                break;
              case "title":
                emp.title = content;
                break;
              case "pages":
                emp.pages = content;
                break;
              case "year":
                emp.year = content;
                break;
              case "volume":
                emp.volume = content;
                break;
              case "journal":
                emp.journal = content;
                break;
              case "number":
                  emp.number = content;
                  break;
              case "url":
                  emp.url = content;
                  break;
              case "ee":
                  emp.ee = content;
                  break;
            }
          }
        }
        return emp;
	}
}
