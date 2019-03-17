package mydb.firstproject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
 
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;

public class FirstProject {
	 Logger logger = LoggerFactory.getLogger(FirstProject.class);
	 
  public static void main(final String[] args) {
	  final ArangoDB arangoDB = new ArangoDB.Builder().build();
	  
	  //create database
	  final String dbName = "mydb10";
	  try {
		  arangoDB.createDatabase(dbName);
		  System.out.println("Database created: " + dbName);
	  }
	  catch(final ArangoDBException e) {
		  System.err.println("Faied to create database: " + dbName + "; " + e.getMessage());
	  }
	  
	  //create collection
	  String collectionName = "firstCollection";
	  try {
	    CollectionEntity myArangoCollection = arangoDB.db(dbName).createCollection(collectionName);
	    System.out.println("Collection created: " + myArangoCollection.getName());
	  } catch (ArangoDBException e) {
	    System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
	  }
	  
	  //creating a document
	  BaseDocument myObject = new BaseDocument();
	  myObject.setKey("myKey");
	  myObject.addAttribute("a", "Foo");
	  myObject.addAttribute("b", 42);
	  try {
	    arangoDB.db(dbName).collection(collectionName).insertDocument(myObject);
	    System.out.println("Document created");
	  } catch (ArangoDBException e) {
	    System.err.println("Failed to create document. " + e.getMessage());
	  }
	  
	  //reading the created document
	  try {
		  BaseDocument myDocument = arangoDB.db(dbName).collection(collectionName).getDocument("myKey",
		      BaseDocument.class);
		  System.out.println("Key: " + myDocument.getKey());
		  System.out.println("Attribute a: " + myDocument.getAttribute("a"));
		  System.out.println("Attribute b: " + myDocument.getAttribute("b"));
		} catch (ArangoDBException e) {
		  System.err.println("Failed to get document: myKey; " + e.getMessage());
		}

	  //updating the document
	  myObject.addAttribute("c", "Bar");
	  try {
		  arangoDB.db(dbName).collection(collectionName).updateDocument("myKey", myObject);
	  } catch (ArangoDBException e) {
		  System.err.println("Failed to update document. " + e.getMessage());
	  }
	  
	  //Reading the document again
	  try {
		  BaseDocument myUpdatedDocument = arangoDB.db(dbName).collection(collectionName).getDocument("myKey",
		      BaseDocument.class);
		  System.out.println("Key: " + myUpdatedDocument.getKey());
		  System.out.println("Attribute a: " + myUpdatedDocument.getAttribute("a"));
		  System.out.println("Attribute b: " + myUpdatedDocument.getAttribute("b"));
		  System.out.println("Attribute c: " + myUpdatedDocument.getAttribute("c"));
		} catch (ArangoDBException e) {
		  System.err.println("Failed to get document: myKey; " + e.getMessage());
		}
	  
	  //updating the document
	  myObject.addAttribute("c", "Bar");
	  try {
	    arangoDB.db(dbName).collection(collectionName).updateDocument("myKey", myObject);
	  } catch (ArangoDBException e) {
	    System.err.println("Failed to update document. " + e.getMessage());
	  }
	  
	  //reading the document
	  try {
		  BaseDocument myUpdatedDocument = arangoDB.db(dbName).collection(collectionName).getDocument("myKey",
		      BaseDocument.class);
		  System.out.println("Key: " + myUpdatedDocument.getKey());
		  System.out.println("Attribute a: " + myUpdatedDocument.getAttribute("a"));
		  System.out.println("Attribute b: " + myUpdatedDocument.getAttribute("b"));
		  System.out.println("Attribute c: " + myUpdatedDocument.getAttribute("c"));
		} catch (ArangoDBException e) {
		  System.err.println("Failed to get document: myKey; " + e.getMessage());
		}
	  
	  //delete a document
	  try {
		  arangoDB.db(dbName).collection(collectionName).deleteDocument("myKey");
		} catch (ArangoDBException e) {
		  System.err.println("Failed to delete document. " + e.getMessage());
		}
	  
	  //creating a document for AQL queries
	  ArangoCollection collection = arangoDB.db(dbName).collection(collectionName);
	  for (int i = 0; i < 10; i++) {
	    BaseDocument value = new BaseDocument();
	    value.setKey(String.valueOf(i));
	    value.addAttribute("name", "Homer");
	    collection.insertDocument(value);
	  }
	  
	 /* 
	  //AQL queries
	  try {
		  String query = "FOR t IN firstCollection FILTER t.name == @name RETURN t";
		  Map<String, Object> bindVars = new MapBuilder().put("name", "Homer").get();
		  ArangoCursor<BaseDocument> cursor = arangoDB.db(dbName).query(query, bindVars, null,
		      BaseDocument.class);
		  cursor.forEachRemaining(aDocument -> {
		    System.out.println("Key: " + aDocument.getKey());
		  });
		} catch (ArangoDBException e) {
		  System.err.println("Failed to execute query. " + e.getMessage());
		}
	  */
	  
  }
}
