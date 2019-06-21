package sell;

public class Sell_notes_firebase_variables {
    String phone_no;
    String price;
    String unit_name;

    public Sell_notes_firebase_variables(String unit_name, String price, String phone_no) {
        this.unit_name = unit_name;
        this.price = price;
        this.phone_no = phone_no;
    }

    public String getPrice() {
        return this.price;
    }

    public String getPhone_no() {
        return this.phone_no;
    }

    public String getUnit_name() {
        return this.unit_name;
    }
}
