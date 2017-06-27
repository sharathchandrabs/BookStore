
public class Books {
	String isbn, title, author, qty, price, yearpublished;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getYearpublished() {
		return yearpublished;
	}

	public void setYearpublished(String yearpublished) {
		this.yearpublished = yearpublished;
	}

	@Override
	public String toString() {
		return "Books [isbn=" + isbn + ", title=" + title + ", author=" + author + ", qty=" + qty + ", price=" + price
				+ ", yearpublished=" + yearpublished + "]";
	}
	
}
