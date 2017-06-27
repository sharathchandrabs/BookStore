
public class Orders {
	String cid, isbn, qty, orderday , cardnumber , shipday;

	@Override
	public String toString() {
		return "Orders [cid=" + cid + ", isbn=" + isbn + ", qty=" + qty + ", orderday=" + orderday + ", cardnumber="
				+ cardnumber + ", shipday=" + shipday + "]";
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getOrderday() {
		return orderday;
	}

	public void setOrderday(String orderday) {
		this.orderday = orderday;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getShipday() {
		return shipday;
	}

	public void setShipday(String shipday) {
		this.shipday = shipday;
	}
}
