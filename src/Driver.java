import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.*;
import javafx.stage.*;
import javafx.util.Callback;
public class Driver extends Application {
	
	Button btn;
	ResultSet myRs;
	Stage window;
	private ObservableList<ObservableList> data;
    private TableView<Customers> table;
    private TableView<Books> tableBooks;
    private TableView<Orders> tableOrders;
    Button backButton;
    Button booksBtn;
    Button ordersBtn;
    Button customerInsertButton;
    Button booksInsertButton;
    Button ordersInsertButton;
    
	
	public static void main(String[] args){
		launch(args);		
		}
	
	public ResultSet runQuery(String query){
	Connection myConn;
	try {
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database123", "root", "sharath1994");
		Statement myStmt = myConn.createStatement();
		myRs = myStmt.executeQuery(query);
		
		/*while(myRs.next()){
			System.out.println(myRs.getString("sid") +", " +myRs.getString("sname") + " ");
			} */
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return myRs;
	
	
	}
	
	public boolean insertQuery(String query){
		Connection myConn;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database123", "root", "sharath1994");
			Statement myStmt = myConn.createStatement();
			/*String query = "INSERT INTO users VALUES(NULL,('" + last_name + "'),('"
		            + first_name + "'),('" + email + "'),('" + password + "'),('"
		            + confirm_password + "'),('" + phone + "'))";*/
			System.out.println(query);
		    myStmt.executeUpdate(query);
		    return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		window = arg0;
		window.setTitle("Database Project");
		Text heading = new Text(50, 50, "Database Project 2");
		Text searchOps = new Text(10,10,"\nSearch Operations:\n");
		Text insertOps = new Text(10,10,"\nInsert Operations:\n");
		
		
		
		
		backButton = new Button();
		backButton.setText("Back");
		btn = new Button();
		btn.setText("Search customers");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				TextField customer = new TextField();
				TextField cid = new TextField();
				TextField caddress = new TextField();
				
				Button searchCust = new Button();
				Button getAllBtn = new Button();
				getAllBtn.setText("View All Values");
				searchCust.setText("Search");
				cid.setPromptText("Enter Customer ID");
				customer.setPromptText("Enter Customer name");
				caddress.setPromptText("Enter Customer address");
				VBox vbox = new VBox(3);
				vbox.getChildren().addAll(customer,cid,caddress, searchCust, backButton, getAllBtn);
				Scene scene = new Scene(vbox, 1500, 1000);
				window.setScene(scene);
				window.show();
				
				getAllBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println(customer.getText().toString());
						String query = "Select * from customers";
						ResultSet queryResult = runQuery(query);
						
						TableColumn<Customers, String> idColumn = new TableColumn<>("Cid");
						idColumn.setMinWidth(200);
						idColumn.setCellValueFactory(new PropertyValueFactory<>("cid"));
						
						TableColumn<Customers, String> cname = new TableColumn<>("Cname");
						cname.setMinWidth(200);
						cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
						
						TableColumn<Customers, String> caddress = new TableColumn<>("Caddress");
						caddress.setMinWidth(200);
						caddress.setCellValueFactory(new PropertyValueFactory<>("caddress"));
						
						table = new TableView();
						table.setItems(getCustomersList(queryResult));
						table.getColumns().addAll(idColumn, cname, caddress);
						
						VBox vbox = new VBox();
						vbox.getChildren().addAll(table, backButton);
						Scene scene = new Scene(vbox, 1500, 1000);
						window.setScene(scene);
						window.show();
					}
				});
				
				backButton.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						VBox vbox = new VBox(5);
						HBox layout = new HBox(5);
						layout.getChildren().addAll( btn, booksBtn, ordersBtn);
						HBox insertionButtons = new HBox(5);
						insertionButtons.getChildren().addAll(customerInsertButton, booksInsertButton, ordersInsertButton);
						vbox.getChildren().addAll(heading,searchOps, layout, insertOps, insertionButtons);
						Scene scene = new Scene(vbox, 600, 300);
						window.setScene(scene);
						window.show();
						
					}
				});
				
				
				searchCust.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println(customer.getText().toString());
						ResultSet queryResult = null;
						if(cid.getText().trim().equals("") && caddress.getText().trim().equals("") ){
							String query = "Select * from customers where cname='"+customer.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(customer.getText().trim().equals("")&&caddress.getText().equals("")){
							String query = "Select * from customers where cid='"+cid.getText().trim()+"'";
							queryResult = runQuery(query);	
						}
						else if(customer.getText().trim().equals("")&&cid.getText().equals("")){
							String query = "Select * from customers where caddress='"+caddress.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(customer.getText().trim().equals("")){
							String query = "Select * from customers where caddress='"+caddress.getText().trim()+"' AND cid='"+cid.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(cid.getText().trim().equals("")){
							String query = "Select * from customers where caddress='"+caddress.getText().trim()+"' AND cname='"+customer.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else {
							String query = "Select * from customers where cname='"+customer.getText().trim()+"' AND cid='"+cid.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						
						TableColumn<Customers, String> idColumn = new TableColumn<>("Cid");
						idColumn.setMinWidth(200);
						idColumn.setCellValueFactory(new PropertyValueFactory<>("cid"));
						
						TableColumn<Customers, String> cname = new TableColumn<>("Cname");
						cname.setMinWidth(200);
						cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
						
						TableColumn<Customers, String> caddress = new TableColumn<>("Caddress");
						caddress.setMinWidth(200);
						caddress.setCellValueFactory(new PropertyValueFactory<>("caddress"));
						
						table = new TableView();
						table.setItems(getCustomersList(queryResult));
						table.getColumns().addAll(idColumn, cname, caddress);
						
						VBox vbox = new VBox();
						vbox.getChildren().addAll(table, backButton);
						Scene scene = new Scene(vbox, 1500, 1000);
						window.setScene(scene);
						window.show();
					}
				});
				
			
				
			}
		});
		
		booksBtn = new Button();
		booksBtn.setText("Search Books");		
		booksBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				TextField book = new TextField();
				TextField isbn = new TextField();
				TextField author = new TextField();
				book.setPromptText("Enter Book title");
				isbn.setPromptText("Enter book ISBN");
				author.setPromptText("Enter Author Name");
				Button searchCust = new Button();
				searchCust.setText("Search");
				Button getAllBtn = new Button();
				getAllBtn.setText("View All Values");
				VBox vbox = new VBox(3);
				vbox.getChildren().addAll(book,isbn,author, searchCust, backButton, getAllBtn);
				Scene scene = new Scene(vbox, 1500, 1000);
				window.setScene(scene);
				window.show();
				getAllBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println(book.getText().toString());
						String query = "Select * from Books";
						ResultSet queryResult = runQuery(query);
						
						TableColumn<Books, String> isbn = new TableColumn<>("ISBN");
						isbn.setMinWidth(200);
						isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
						
						TableColumn<Books, String> title = new TableColumn<>("Title");
						title.setMinWidth(200);
						title.setCellValueFactory(new PropertyValueFactory<>("title"));
						
						TableColumn<Books, String> author = new TableColumn<>("Author");
						author.setMinWidth(200);
						author.setCellValueFactory(new PropertyValueFactory<>("author"));
						
						TableColumn<Books, String> qty = new TableColumn<>("Quantity");
						qty.setMinWidth(200);
						qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
						
						TableColumn<Books, String> price = new TableColumn<>("Price");
						price.setMinWidth(200);
						price.setCellValueFactory(new PropertyValueFactory<>("price"));
						
						TableColumn<Books, String> yearpublished = new TableColumn<>("Year Published");
						yearpublished.setMinWidth(200);
						yearpublished.setCellValueFactory(new PropertyValueFactory<>("yearpublished"));
						
						tableBooks = new TableView();
						tableBooks.setItems(getBooksList(queryResult));
						tableBooks.getColumns().addAll(isbn, title, author, qty , price , yearpublished);
						
						VBox vbox = new VBox();
						vbox.getChildren().addAll(tableBooks, backButton);
						Scene scene = new Scene(vbox, 1500, 1000);
						window.setScene(scene);
						window.show();
					}
				});
				backButton.setOnAction(new EventHandler<ActionEvent>() {
									
									@Override
									public void handle(ActionEvent event) {
										// TODO Auto-generated method stub
										VBox vbox = new VBox(5);
										HBox layout = new HBox(5);
										layout.getChildren().addAll( btn, booksBtn, ordersBtn);
										HBox insertionButtons = new HBox(5);
										insertionButtons.getChildren().addAll(customerInsertButton, booksInsertButton, ordersInsertButton);
										vbox.getChildren().addAll(heading,searchOps, layout, insertOps, insertionButtons);
										Scene scene = new Scene(vbox, 600, 300);
										window.setScene(scene);
										window.show();
										
									}
								});
				searchCust.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println(book.getText().toString());
						
						ResultSet queryResult = null;
						if(book.getText().trim().equals("") && isbn.getText().trim().equals("") ){
							String query = "Select * from books where author='"+author.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(book.getText().trim().equals("")&&author.getText().equals("")){
							String query = "Select * from books where isbn='"+isbn.getText().trim()+"'";
							queryResult = runQuery(query);	
						}
						else if(isbn.getText().trim().equals("")&&author.getText().equals("")){
							String query = "Select * from books where title='"+book.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(book.getText().trim().equals("")){
							String query = "Select * from books where isbn='"+isbn.getText().trim()+"' AND author='"+author.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(author.getText().trim().equals("")){
							String query = "Select * from books where isbn='"+isbn.getText().trim()+"' AND title='"+book.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else {
							String query = "Select * from books where author='"+author.getText().trim()+"' AND title='"+book.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						
						TableColumn<Books, String> isbn = new TableColumn<>("ISBN");
						isbn.setMinWidth(200);
						isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
						
						TableColumn<Books, String> title = new TableColumn<>("Title");
						title.setMinWidth(200);
						title.setCellValueFactory(new PropertyValueFactory<>("title"));
						
						TableColumn<Books, String> author = new TableColumn<>("Author");
						author.setMinWidth(200);
						author.setCellValueFactory(new PropertyValueFactory<>("author"));
						
						TableColumn<Books, String> qty = new TableColumn<>("Quantity");
						qty.setMinWidth(200);
						qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
						
						TableColumn<Books, String> price = new TableColumn<>("Price");
						price.setMinWidth(200);
						price.setCellValueFactory(new PropertyValueFactory<>("price"));
						
						TableColumn<Books, String> yearpublished = new TableColumn<>("Year Published");
						yearpublished.setMinWidth(200);
						yearpublished.setCellValueFactory(new PropertyValueFactory<>("yearpublished"));
						
						tableBooks = new TableView();
						tableBooks.setItems(getBooksList(queryResult));
						tableBooks.getColumns().addAll(isbn, title, author, qty , price , yearpublished);
						
						VBox vbox = new VBox();
						vbox.getChildren().addAll(tableBooks, backButton);
						Scene scene = new Scene(vbox, 1500, 1000);
						window.setScene(scene);
						window.show();
					}
				});
				
			}
		});
		
		ordersBtn = new Button();
		ordersBtn.setText("View Orders");
		ordersBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				TextField cid = new TextField();
				TextField isbn = new TextField();
				TextField qty = new TextField();
				TextField cardnum = new TextField();
				cid.setPromptText("Enter Customer ID");
				isbn.setPromptText("Enter ISBN");
				qty.setPromptText("Enter Quantity");
				cardnum.setPromptText("Enter Card Number");
				Button searchCust = new Button();
				searchCust.setText("Search");
				Button getAllBtn = new Button();
				getAllBtn.setText("View All Values");
				VBox vbox = new VBox(3);
				vbox.getChildren().addAll(cid, isbn, qty, cardnum, searchCust, backButton, getAllBtn);
				Scene scene = new Scene(vbox, 1500, 1000);
				window.setScene(scene);
				window.show();
				getAllBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println(cid.getText().toString());
						String query = "Select * from Orders";
						ResultSet queryResult = runQuery(query);
						
						TableColumn<Orders, String> isbn = new TableColumn<>("ISBN");
						isbn.setMinWidth(200);
						isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
						
						TableColumn<Orders, String> cid = new TableColumn<>("Cid");
						cid.setMinWidth(200);
						cid.setCellValueFactory(new PropertyValueFactory<>("cid"));
						
						TableColumn<Orders, String> orderday = new TableColumn<>("Order Day");
						orderday.setMinWidth(200);
						orderday.setCellValueFactory(new PropertyValueFactory<>("orderday"));
						
						TableColumn<Orders, String> cardnumber = new TableColumn<>("Card Number");
						cardnumber.setMinWidth(200);
						cardnumber.setCellValueFactory(new PropertyValueFactory<>("cardnumber"));
						
						TableColumn<Orders, String> shipday = new TableColumn<>("Ship Date");
						shipday.setMinWidth(200);
						shipday.setCellValueFactory(new PropertyValueFactory<>("shipday"));
						
						TableColumn<Orders, String> qty = new TableColumn<>("Quantity");
						qty.setMinWidth(200);
						qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
						
						tableOrders = new TableView();
						tableOrders.setItems(getOrdersList(queryResult));
						tableOrders.getColumns().addAll(cid, isbn, qty, orderday , cardnumber , shipday);
						
						VBox vbox = new VBox();
						vbox.getChildren().addAll(tableOrders, backButton);
						Scene scene = new Scene(vbox, 1500, 1000);
						window.setScene(scene);
						window.show();
					}
				});
				
				backButton.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						VBox vbox = new VBox(5);
						HBox layout = new HBox(5);
						layout.getChildren().addAll( btn, booksBtn, ordersBtn);
						HBox insertionButtons = new HBox(5);
						insertionButtons.getChildren().addAll(customerInsertButton, booksInsertButton, ordersInsertButton);
						vbox.getChildren().addAll(heading,searchOps, layout, insertOps, insertionButtons);
						Scene scene = new Scene(vbox, 600, 300);
						window.setScene(scene);
						window.show();
						
					}
				});
				
				searchCust.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println(cid.getText().toString());
						ResultSet queryResult = null;
						if(cid.getText().trim().equals("") && isbn.getText().trim().equals("") && qty.getText().trim().equals("")){
							String query = "Select * from orders where cardnum='"+cardnum.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(isbn.getText().trim().equals("")&&qty.getText().equals("")&& cardnum.getText().trim().equals("")){
							String query = "Select * from orders where cid='"+cid.getText().trim()+"'";
							queryResult = runQuery(query);	
						}
						else if(qty.getText().trim().equals("")&&cardnum.getText().equals("")&& cid.getText().trim().equals("")){
							String query = "Select * from orders where isbn='"+isbn.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(cardnum.getText().trim().equals("")&&cid.getText().equals("")&& isbn.getText().trim().equals("")){
							String query = "Select * from orders where qty>='"+qty.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(cid.getText().trim().equals("") && isbn.getText().trim().equals("")){
							String query = "Select * from orders where qty='"+qty.getText().trim()+"' AND cardnum='"+cardnum.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(isbn.getText().trim().equals("") && qty.getText().trim().equals("")){
							String query = "Select * from orders where cid='"+cid.getText().trim()+"' AND cardnum='"+cardnum.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(qty.getText().trim().equals("") && cardnum.getText().trim().equals("")){
							String query = "Select * from orders where cid='"+cid.getText().trim()+"' AND isbn='"+isbn.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(cardnum.getText().trim().equals("") && isbn.getText().trim().equals("")){
							String query = "Select * from orders where cid='"+cid.getText().trim()+"' AND qty='"+qty.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(cid.getText().trim().equals("")){
							String query = "Select * from orders where isbn='"+isbn.getText().trim()+"' AND qty='"+qty.getText().trim()+"' AND cardnum='"+cardnum.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(isbn.getText().trim().equals("")){
							String query = "Select * from orders where cid='"+isbn.getText().trim()+"' AND qty='"+qty.getText().trim()+"' AND cardnum='"+cardnum.getText().trim()+"'";
							queryResult = runQuery(query);						
						}
						else if(qty.getText().trim().equals("")){
							String query = "Select * from orders where isbn='"+isbn.getText().trim()+"' AND cid='"+cid.getText().trim()+"' AND cardnum='"+cardnum.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else if(cardnum.getText().trim().equals("")){
							String query = "Select * from orders where isbn='"+isbn.getText().trim()+"' AND qty='"+qty.getText().trim()+"' AND cid='"+cid.getText().trim()+"'";
							queryResult = runQuery(query);
						}
						else{
							String query = "Select * from orders where isbn='"+isbn.getText().trim()+"' AND qty='"+qty.getText().trim()+"' AND cid='"+cid.getText().trim()+"' AND cardnum='"+cardnum.getText().trim()+"'";
							queryResult = runQuery(query);
							System.out.println(query);
						}
						
						TableColumn<Orders, String> isbn = new TableColumn<>("ISBN");
						isbn.setMinWidth(200);
						isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
						
						TableColumn<Orders, String> cid = new TableColumn<>("Cid");
						cid.setMinWidth(200);
						cid.setCellValueFactory(new PropertyValueFactory<>("cid"));
						
						TableColumn<Orders, String> orderday = new TableColumn<>("Order Day");
						orderday.setMinWidth(200);
						orderday.setCellValueFactory(new PropertyValueFactory<>("orderday"));
						
						TableColumn<Orders, String> cardnumber = new TableColumn<>("Card Number");
						cardnumber.setMinWidth(200);
						cardnumber.setCellValueFactory(new PropertyValueFactory<>("cardnumber"));
						
						TableColumn<Orders, String> shipday = new TableColumn<>("Ship Date");
						shipday.setMinWidth(200);
						shipday.setCellValueFactory(new PropertyValueFactory<>("shipday"));
						
						TableColumn<Orders, String> qty = new TableColumn<>("Quantity");
						qty.setMinWidth(200);
						qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
						
						tableOrders = new TableView();
						tableOrders.setItems(getOrdersList(queryResult));
						tableOrders.getColumns().addAll(cid, isbn, qty, orderday , cardnumber , shipday);
						
						VBox vbox = new VBox();
						vbox.getChildren().addAll(tableOrders, backButton);
						Scene scene = new Scene(vbox, 1500, 1000);
						window.setScene(scene);
						window.show();
					}
				});
				
			}
		});
		
		
		customerInsertButton = new Button();
		customerInsertButton.setText("Add Customer");
		customerInsertButton.setOnAction(new EventHandler<ActionEvent>() {

			TextField cid = new TextField();
			TextField cname = new TextField();
			TextField caddress = new TextField();
			Button customerSubmit = new Button();
			Text queryResult = new Text();
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				cid.setPromptText("Enter Customer ID");
				cname.setPromptText("Enter Customer Name");
				caddress.setPromptText("Enter Customer Address");
				customerSubmit.setText("Submit");
				queryResult.setText("");
				VBox vbox = new VBox(3);
				vbox.getChildren().addAll(cid, cname, caddress, customerSubmit, backButton , queryResult);
				Scene scene = new Scene(vbox, 1500, 1000);
				window.setScene(scene);
				window.show();
				
				backButton.setOnAction(new EventHandler<ActionEvent>() {
									
									@Override
									public void handle(ActionEvent event) {
										// TODO Auto-generated method stub
										VBox vbox = new VBox(5);
										HBox layout = new HBox(5);
										layout.getChildren().addAll( btn, booksBtn, ordersBtn);
										HBox insertionButtons = new HBox(5);
										insertionButtons.getChildren().addAll(customerInsertButton, booksInsertButton, ordersInsertButton);
										vbox.getChildren().addAll(heading,searchOps, layout, insertOps, insertionButtons);
										Scene scene = new Scene(vbox, 600, 300);
										window.setScene(scene);
										window.show();
										
									}
								});
				
				customerSubmit.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(cid.getText().equals("") || cname.getText().equals("") || caddress.getText().equals("")){
							queryResult.setText("Please check the entered values.");
						}else{
						String query = "INSERT INTO customers VALUES(('" + cid.getText().trim() + "'),('"
					            + cname.getText().trim() + "'),('" + caddress.getText().trim() + "'))";
						if(insertQuery(query)){
							queryResult.setText("Record successfully added");
						}
						else{
							queryResult.setText("Record insertion unsuccessful");
						}
						}
						
					}
				});
				
			}
		});
		
		
		booksInsertButton = new Button();
		booksInsertButton.setText("Add Books");
		booksInsertButton.setOnAction(new EventHandler<ActionEvent>() {
			TextField isbn = new TextField();
			TextField title = new TextField();
			TextField author = new TextField();
			TextField qty = new TextField();
			TextField price = new TextField();
			TextField yearpublished = new TextField();
			Button customerSubmit = new Button();
			Text queryResult = new Text();
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				isbn.setPromptText("Enter ISBN");
				title.setPromptText("Enter Title");
				author.setPromptText("Enter Author");
				qty.setPromptText("Enter Quantity");
				price.setPromptText("Enter Price");
				yearpublished.setPromptText("Enter Published Year");
				customerSubmit.setText("Submit");
				queryResult.setText("");
				VBox vbox = new VBox(9);
				vbox.getChildren().addAll(isbn, title, author, qty, price, yearpublished, customerSubmit,backButton, queryResult);
				Scene scene = new Scene(vbox, 1500, 1000);
				window.setScene(scene);
				window.show();
				backButton.setOnAction(new EventHandler<ActionEvent>() {
									
									@Override
									public void handle(ActionEvent event) {
										// TODO Auto-generated method stub
										VBox vbox = new VBox(5);
										HBox layout = new HBox(5);
										layout.getChildren().addAll( btn, booksBtn, ordersBtn);
										HBox insertionButtons = new HBox(5);
										insertionButtons.getChildren().addAll(customerInsertButton, booksInsertButton, ordersInsertButton);
										vbox.getChildren().addAll(heading,searchOps, layout, insertOps, insertionButtons);
										Scene scene = new Scene(vbox, 600, 300);
										window.setScene(scene);
										window.show();
										
									}
								});
				
				customerSubmit.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(isbn.getText().equals("") || title.getText().equals("") || author.getText().equals("") || qty.getText().equals("") || price.getText().equals("") || yearpublished.getText().equals("")){
							queryResult.setText("Please check the entered values.");
						}else{
						String query = "INSERT INTO books VALUES(('" + isbn.getText().trim() + "'),('"
					            + title.getText().trim() + "'),('" + author.getText().trim() + "'),('" + qty.getText().trim() + "'),('" + price.getText().trim() + "'),('" + yearpublished.getText().trim() + "'))";
						if(insertQuery(query)){
							queryResult.setText("Record successfully added");
						}
						else{
							queryResult.setText("Record insertion unsuccessful");
						}
						}
					}
				});
			}
		});
		
		ordersInsertButton = new Button();
		ordersInsertButton.setText("Place Orders");
		ordersInsertButton.setOnAction(new EventHandler<ActionEvent>() {
			TextField cid = new TextField();
			TextField isbn = new TextField();
			TextField qty = new TextField();
			TextField orderday = new TextField();
			TextField cardnum = new TextField();
			TextField shipday = new TextField();
			Button customerSubmit = new Button();
			Text queryResult = new Text();
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				cid.setPromptText("Enter Customer ID");
				isbn.setPromptText("Enter ISBN");
				qty.setPromptText("Enter Quantity");
				orderday.setPromptText("Enter Order Date");
				cardnum.setPromptText("Enter Card Number");
				shipday.setPromptText("Enter Ship Date");
				customerSubmit.setText("Submit");
				queryResult.setText("");
				VBox vbox = new VBox(9);
				vbox.getChildren().addAll(cid, isbn, qty, orderday, cardnum, shipday, customerSubmit, backButton, queryResult);
				Scene scene = new Scene(vbox, 1500, 1000);
				window.setScene(scene);
				window.show();
				
				backButton.setOnAction(new EventHandler<ActionEvent>() {
									
									@Override
									public void handle(ActionEvent event) {
										// TODO Auto-generated method stub
										VBox vbox = new VBox(5);
										HBox layout = new HBox(5);
										layout.getChildren().addAll( btn, booksBtn, ordersBtn);
										HBox insertionButtons = new HBox(5);
										insertionButtons.getChildren().addAll(customerInsertButton, booksInsertButton, ordersInsertButton);
										vbox.getChildren().addAll(heading,searchOps, layout, insertOps, insertionButtons);
										Scene scene = new Scene(vbox, 600, 300);
										window.setScene(scene);
										window.show();
										
									}
								});
				
				customerSubmit.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						
						// TODO Auto-generated method stub
						if(isbn.getText().equals("") || cid.getText().equals("") || orderday.getText().equals("") || qty.getText().equals("") || cardnum.getText().equals("") || shipday.getText().equals("")){
							queryResult.setText("Please check the entered values.");
						}else{
							SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
							
							try {
								java.util.Date dateordered = sdf1.parse(orderday.getText().toString());
								java.util.Date dateshipped = sdf1.parse(shipday.getText().toString());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								queryResult.setText("Date conversion error.");
							}
							
						
						String query = "INSERT INTO orders VALUES(('" + cid.getText().trim() + "'),('"
					            + isbn.getText().trim() + "'),('" + qty.getText().trim() + "'),('" + orderday.getText().trim() + "'),('" + cardnum.getText().trim() + "'),('" + shipday.getText().trim() + "'))";
						if(insertQuery(query)){
							queryResult.setText("Record successfully added");
						}
						else{
							queryResult.setText("Record insertion unsuccessful");
						}
						}
					}
				});
			}
		});
		
		
		
		
		
		VBox vbox = new VBox(5);
		HBox layout = new HBox(5);
		layout.getChildren().addAll( btn, booksBtn, ordersBtn);
		HBox insertionButtons = new HBox(5);
		insertionButtons.getChildren().addAll(customerInsertButton, booksInsertButton, ordersInsertButton);
		vbox.getChildren().addAll(heading,searchOps, layout, insertOps, insertionButtons);
		Scene scene = new Scene(vbox, 800, 600);
		window.setScene(scene);
		window.show();
		
	}
	
	public ObservableList<Customers> getCustomersList(ResultSet queryresult){
		ObservableList<Customers> customerDetails = FXCollections.observableArrayList();
		try {
			while(queryresult.next()){
				Customers c = new Customers();
				c.setCid(queryresult.getString("cid"));
				c.setCname(queryresult.getString("cname"));
				c.setCaddress(queryresult.getString("caddress"));
				//System.out.println(myRs.getString("cname") +", " +myRs.getString("caddress") + " ");
				System.out.println(c.toString());
				customerDetails.add(c);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerDetails;
		
	}

	public ObservableList<Books> getBooksList(ResultSet queryresult){
		ObservableList<Books> BooksDetails = FXCollections.observableArrayList();
		try {
			while(queryresult.next()){
				Books b = new Books();
				b.setIsbn(queryresult.getString("isbn"));
				b.setTitle(queryresult.getString("title"));
				b.setAuthor(queryresult.getString("author"));
				b.setQty(queryresult.getString("qty"));
				b.setPrice(queryresult.getString("price"));
				b.setYearpublished(queryresult.getString("yearpublished"));
				//System.out.println(myRs.getString("cname") +", " +myRs.getString("caddress") + " ");
				System.out.println(b.toString());
				BooksDetails.add(b);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BooksDetails;
		
	}
	
	public ObservableList<Orders> getOrdersList(ResultSet queryresult){
		ObservableList<Orders> OrdersDetails = FXCollections.observableArrayList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				while(queryresult.next()){
					Orders b = new Orders();
					b.setCid(queryresult.getString("cid"));
					b.setIsbn(queryresult.getString("isbn"));
					b.setQty(queryresult.getString("qty"));
					b.setOrderday(dateFormat.format(queryresult.getDate("orderday")));
					b.setCardnumber(queryresult.getString("cardnum"));
					b.setShipday(dateFormat.format(queryresult.getDate("shipday")));
					//System.out.println(myRs.getString("cname") +", " +myRs.getString("caddress") + " ");
					System.out.println(b.toString());
					OrdersDetails.add(b);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return OrdersDetails;
	
	}

}
	
	

