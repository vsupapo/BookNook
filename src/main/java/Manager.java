import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.client.ClientSession;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Projections.excludeId;

public class Manager {
	static MongoDatabase db;
	// static MongoCollection<Document> collection;

	public static void main(String[] args) {

		MongoClient client = MongoClients.create("mongodb://34.224.86.42:27017");
		System.out.println("Mongo Connection established successfully");
		// displayAllDatabases(client);
		System.out.println("Connecting to database");
		db = client.getDatabase("testdb");
		// displayAllCollections();
		System.out.println("Connection successful");
		boolean exit = false;
		while (!exit) {
			System.out.println("\n\nChoose the required operation :");
			System.out.println(
					"\n\n1. Insert document\n2. Delete document\n3. Update document\n4. Search for book by title\n5. Display top 10 ranked books in certain category\n"
							+ "6. Display top ranked book for every category\n7. Display description of certain book\n"
							+ "8. Display books by certain publisher\n9. Display books by certain author\n10. Display books with the most number of weeks as bestseller\n"
							+ "11. Display all documents in category\n12. List the subjects of certain book in general category\n"
							+ "13. Display books by subject place\n14. Display all links to buy a certain book\n"
							+ "15. Display books that stay on bestseller list for certain week\n16. Exit\n\n");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				insertBook();
				break;
			case 2:
				deleteBook();
				break;
			case 3:
				updateBook();
				break;			
			case 4:
				searchByTitle();
				break;
			case 5:
				displayTopTenInCategory();
				break;
			case 6:
				displayTopRankedInAllCategory();
				break;
			case 7:
				displayDescription();
				break;
			case 8:
				findPublisher();
				break;
			case 9:
				findAuthor();
				break;
			case 10:
				mostWeeksOnListInCategory();
				break;
			case 11:
				displayAllBooksInCategory();
				break;
			case 12:
				findSubjectsByTitle();
				break;
			case 13:
				findBySubjectsPlace();
				break;
			case 14:
				findBuyLinksOfTitle();
				break;
			case 15:
				findByWeeksOnList();
				break;
			case 16:
				System.exit(0);
				break;
			default:
				break;
			}
		}

	}

	private static void insertBook() {
		MongoCollection<Document> collection = getCollection();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter name of book to insert");
		String title = sc.nextLine();
		System.out.println("Enter the ISBN");
		String isbn = sc.nextLine();
		Document doc = new Document("title", title).append("primary_isbn13", isbn).append("status", "new")
				.append("rank", 100).append("weeks_on_list", 0);
		collection.insertOne(doc);
		System.out.println("Document successfully inserted\n");
	}

	private static void deleteBook() {
		Scanner sc = new Scanner(System.in);
		MongoCollection<Document> collection = getCollection();
		System.out.println("Enter ISBN of book to delete");
		String isbn = sc.nextLine();
		collection.deleteOne(Filters.eq("primary_isbn13", isbn));
		System.out.println("Document successfully deleted\n");
	}

	private static void updateBook() {
		Scanner sc = new Scanner(System.in);
		MongoCollection<Document> collection = getCollection();
		System.out.println("Enter ISBN of book to update");
		String isbn = sc.nextLine();
		System.out.println("Enter new rank of book to update");
		int rank = Integer.parseInt(sc.nextLine());

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("rank", rank).append("status", "Updated"));
		BasicDBObject searchQuery = new BasicDBObject().append("primary_isbn13", isbn);
		collection.updateOne(searchQuery, newDocument);

		System.out.println("Update successful. Status of book changed from 'new' to 'Updated'\n");
	}

	private static void displayAllBooksInCategory() {
		MongoCollection<Document> collection = getCollection();
		MongoCursor<Document> cursor = collection.find().iterator();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			System.out.println("Title: "+document.getString("title")+", Author: "+document.getString("author"));
		}
		
	}

	private static void searchByTitle() {
		Scanner sc = new Scanner(System.in);
		MongoCollection<Document> collection = getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		System.out.println("Enter the title of book to search\n");
		String title = sc.nextLine();
		whereQuery.put("title", title);
		MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void displayTopTenInCategory() {
		MongoCollection<Document> collection = getCollection();
		MongoCursor<Document> cursor = collection.find().iterator();
		int count = 0;
		while (cursor.hasNext() && ++count <= 10) {
			System.out.println(cursor.next());
		}
	}

	private static void displayTopRankedInAllCategory() {
		MongoCursor<String> categories = db.listCollectionNames().iterator();
		while (categories.hasNext()) {
			String category = categories.next();
			MongoCollection<Document> collection = db.getCollection(category);
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("rank", 1);
			MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
			if (cursor.hasNext()) {
				System.out.println("category : " + category + "\nBook Details:" + cursor.next() + "\n");
			}
		}
	}

	private static void displayDescription() {
		Scanner sc = new Scanner(System.in);
		MongoCollection<Document> collection = getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		System.out.println("Enter the title of book to search\n");
		String title = sc.nextLine();
		whereQuery.put("title", title);
		MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			System.out.println(document.getString("description"));
		}
	}

	private static void findPublisher() {
		MongoCollection<Document> collection = getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the publisher of book to search");
		String pub = sc.nextLine();
		whereQuery.put("publisher", pub);
		MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void findAuthor() {
		MongoCollection<Document> collection = getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Author of book to search");
		String author = sc.nextLine();
		whereQuery.put("author", author);
		MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void mostWeeksOnListInCategory() {
		MongoCollection<Document> collection = getCollection();
		MongoCursor<Document> cursor = collection.find().sort(Sorts.descending("weeks_on_list")).iterator();
		if (cursor.hasNext()) {
			System.out.println(cursor.next());
		} else
			System.out.println("\nNo data found\n");
	}

	private static void findSubjectsByTitle() {
		MongoCollection<Document> collection = db.getCollection("general_book2");
		BasicDBObject whereQuery = new BasicDBObject();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Title of the book to search");
		String title = sc.nextLine();
		whereQuery.put("title", title);
		MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			List<String> list = (List<String>) document.get("subjects");
			for (String sub : list) {
				System.out.println(sub);
			}
		}
	}

	private static void findBySubjectsPlace() {
		MongoCollection<Document> collection = db.getCollection("general_book2");
		BasicDBObject whereQuery = new BasicDBObject();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the subjects place of the book to search");
		String subjectPlaces = sc.nextLine();
		whereQuery.put("subject_places", subjectPlaces);
		MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void findBuyLinksOfTitle() {
		MongoCollection<Document> collection = getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Title of the book to search");
		String title = sc.nextLine();
		whereQuery.put("title", title);
		MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			List<Document> listOfLists = (List<Document>) document.get("buy_links");
			for (Document doc : listOfLists) {
				System.out.println("\nName: " + doc.get("name"));
				System.out.println("Link: " + doc.get("url") + "\n");
			}
		}
	}

	private static void findByWeeksOnList() {
		MongoCollection<Document> collection = getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of weeks of the book to search");
		int weeks = Integer.parseInt(sc.nextLine());
		// whereQuery.put("weeks_on_list", weeks);
		whereQuery.put("weeks_on_list", new BasicDBObject("$eq", weeks));
		MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			System.out.println("Author: " + document.getString("author") + ", Title: " + document.getString("title")
					+ ", Weeks on list:" + weeks + "\n");
		}
	}

	private static void displayByDate() {
		MongoCollection<Document> collection = getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the date of the books to search");
		String pubDate = sc.nextLine();
		whereQuery.put("date", BasicDBObjectBuilder.start("$gte", pubDate).add("$lte", pubDate).get());
		FindIterable<Document> cursor = collection.find(whereQuery);
		while (((Iterator<String>) cursor).hasNext()) {
			System.out.println(((Iterator<String>) cursor).next());
		}
	}

	private static MongoCollection<Document> getCollection() {
		String category = getCategory();
		return db.getCollection(category);
	}

	private static String getCategory() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the category of the book to search from:\n");
		displayAllCollections();
		String category = sc.nextLine();
		return category;
	}

	private static void displayAllCollections() {
		MongoCursor<String> dbsCursor = db.listCollectionNames().iterator();
		while (dbsCursor.hasNext()) {
			System.out.println(dbsCursor.next());
		}
		System.out.println("\n");
	}

	private static void displayAllDatabases(MongoClient client) {
		MongoCursor<String> dbsCursor = client.listDatabaseNames().iterator();
		while (dbsCursor.hasNext()) {
			System.out.println(dbsCursor.next());
		}
	}
}
