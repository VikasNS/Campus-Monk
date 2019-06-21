package sell;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.campusmonk.vikas.msrit.C0418R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class my_products_recycler_view_adaptor extends Adapter<my_placeholder_products> {
    String USN;
    ArrayList<Sell_notes_firebase_variables> arrayList = new ArrayList();
    RecyclerView recyclerView;

    public class my_placeholder_products extends ViewHolder {
        int AdapterPosition;
        Button delete_product_button;
        Button edit_product_button;
        TextView myproducts_name;
        TextView myproducts_price;

        public my_placeholder_products(View itemView) {
            super(itemView);
            this.myproducts_name = (TextView) itemView.findViewById(C0418R.id.my_products_unit_name);
            this.myproducts_price = (TextView) itemView.findViewById(C0418R.id.my_products_price);
            this.edit_product_button = (Button) itemView.findViewById(C0418R.id.edit_product_button);
            this.delete_product_button = (Button) itemView.findViewById(C0418R.id.delete_product_button);
            this.edit_product_button.setOnClickListener(new OnClickListener(my_products_recycler_view_adaptor.this) {
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), Sell_notes_form.class);
                    i.putExtra("Notes_name", ((Sell_notes_firebase_variables) my_products_recycler_view_adaptor.this.arrayList.get(my_placeholder_products.this.getAdapterPosition())).getUnit_name());
                    v.getContext().startActivity(i);
                }
            });
            this.delete_product_button.setOnClickListener(new OnClickListener(my_products_recycler_view_adaptor.this) {
                public void onClick(View view) {
                    my_placeholder_products.this.AdapterPosition = my_placeholder_products.this.getAdapterPosition();
                    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference usn = root.child("notes_seller").child(my_products_recycler_view_adaptor.this.USN);
                    DatabaseReference UsnNotes = usn.child("notes");
                    DatabaseReference UsnTextBook = usn.child("TextBook");
                    DatabaseReference NotesList = root.child("notes_list");
                    my_products_recycler_view_adaptor.this.CheckAndDelete(UsnTextBook, root.child("TextBookList"), my_placeholder_products.this.AdapterPosition, UsnNotes, NotesList);
                }
            });
        }
    }

    public my_products_recycler_view_adaptor(ArrayList<Sell_notes_firebase_variables> arrayList, String USN, RecyclerView recyclerView) {
        this.arrayList = arrayList;
        this.USN = USN;
        this.recyclerView = recyclerView;
    }

    private void CheckAndDelete(DatabaseReference UsnTextBook, DatabaseReference TextBookList, int AdapterPosition, DatabaseReference UsnNotes, DatabaseReference NotesList) {
        final int i = AdapterPosition;
        final DatabaseReference databaseReference = UsnNotes;
        final DatabaseReference databaseReference2 = NotesList;
        final DatabaseReference databaseReference3 = TextBookList;
        final DatabaseReference databaseReference4 = UsnTextBook;
        UsnNotes.addListenerForSingleValueEvent(new ValueEventListener() {

            /* renamed from: sell.my_products_recycler_view_adaptor$1$1 */
            class C23181 implements ValueEventListener {
                C23181() {
                }

                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.child(((Sell_notes_firebase_variables) my_products_recycler_view_adaptor.this.arrayList.get(i)).getUnit_name()).exists()) {
                            child.child(((Sell_notes_firebase_variables) my_products_recycler_view_adaptor.this.arrayList.get(i)).getUnit_name()).child(my_products_recycler_view_adaptor.this.USN).getRef().removeValue();
                            databaseReference4.child(((Sell_notes_firebase_variables) my_products_recycler_view_adaptor.this.arrayList.get(i)).getUnit_name()).removeValue();
                            my_products_recycler_view_adaptor.this.arrayList.remove(i);
                            my_products_recycler_view_adaptor.this.notifyDataSetChanged();
                            return;
                        }
                    }
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(((Sell_notes_firebase_variables) my_products_recycler_view_adaptor.this.arrayList.get(i)).getUnit_name()).exists()) {
                    databaseReference.child(((Sell_notes_firebase_variables) my_products_recycler_view_adaptor.this.arrayList.get(i)).getUnit_name()).removeValue();
                    databaseReference2.child(((Sell_notes_firebase_variables) my_products_recycler_view_adaptor.this.arrayList.get(i)).getUnit_name()).child(my_products_recycler_view_adaptor.this.USN).removeValue();
                    Log.v("arraylistsize", my_products_recycler_view_adaptor.this.arrayList.size() + "");
                    my_products_recycler_view_adaptor.this.arrayList.remove(i);
                    Log.v("arraylistsize", my_products_recycler_view_adaptor.this.arrayList.size() + "");
                    my_products_recycler_view_adaptor.this.notifyDataSetChanged();
                    return;
                }
                databaseReference3.addListenerForSingleValueEvent(new C23181());
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public my_placeholder_products onCreateViewHolder(ViewGroup parent, int viewType) {
        return new my_placeholder_products(LayoutInflater.from(parent.getContext()).inflate(C0418R.layout.my_products_adaptor_layout, parent, false));
    }

    public void onBindViewHolder(my_placeholder_products holder, int position) {
        holder.myproducts_name.setText(((Sell_notes_firebase_variables) this.arrayList.get(position)).getUnit_name());
        holder.myproducts_price.setText(((Sell_notes_firebase_variables) this.arrayList.get(position)).getPrice());
    }

    public int getItemCount() {
        return this.arrayList.size();
    }
}
