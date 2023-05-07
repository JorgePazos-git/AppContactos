package ec.com.empresa.appagendacontactos_pazosjorge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    dbContactos contactos;
    TableLayout tblContactos;

    TextView tvNombre, tvTelefono, tvDireccion, tvEmail;

    EditText editTextBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactos = new dbContactos(this,"contactos.db", 1);
        tblContactos = findViewById(R.id.tblContactos);
        LlenarTabla();
        editTextBusqueda = findViewById(R.id.txtBuscador);
        buscador();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LlenarTabla();
    }

    private void buscador(){
        editTextBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                filterTable(query);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterTable(String query) {
        for (int i = 1; i < tblContactos.getChildCount(); i++) {
            TableRow row = (TableRow) tblContactos.getChildAt(i);
            TextView textView = (TextView) row.getChildAt(0);
            String text = textView.getText().toString();
            if (text.toLowerCase().contains(query.toLowerCase())) {
                row.setVisibility(View.VISIBLE);
            } else {
                row.setVisibility(View.GONE);
            }
        }
    }

    public void cmdAgregar_onClick(View v){
        Intent i  = new Intent(this, Activity2.class);
        startActivity(i);
    }

    public void cmdBuscar_onClick(View v){
        buscador();
    }

    private void LlenarTabla(){
        if(contactos != null){
            Contacto[] listaContactos = contactos.Read_All();

            TableLayout tabla = findViewById(R.id.tblContactos);
            TableRow headers = (TableRow) tabla.getChildAt(0);
            tabla.removeAllViews();
            tabla.addView(headers);

            if(listaContactos != null){
                for (Contacto contacto : listaContactos) {
                    TableRow fila = new TableRow(this);

                    tvNombre = new TextView(this);
                    tvNombre.setText(contacto.nombreContac);
                    tvNombre.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvNombre.setPadding(10, 10, 10, 10);
                    tvNombre.setTag("tvNombre");
                    fila.addView(tvNombre);

                    tvTelefono = new TextView(this);
                    tvTelefono.setText(contacto.telefonoContac);
                    tvTelefono.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvTelefono.setPadding(10, 10, 10, 10);
                    fila.addView(tvTelefono);

                    tvDireccion = new TextView(this);
                    tvDireccion.setText(contacto.direccionContac);
                    tvDireccion.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvDireccion.setPadding(10, 10, 10, 10);
                    fila.addView(tvDireccion);

                    tvEmail = new TextView(this);
                    tvEmail.setText(contacto.emailContac);
                    tvEmail.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvEmail.setPadding(10, 10, 10, 10);
                    fila.addView(tvEmail);

                    tabla.addView(fila);

                    fila.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Abre la nueva ventana
                            Intent intent = new Intent(getApplicationContext(), Activity2.class);
                            intent.putExtra("idContac", contacto.idContac);
                            intent.putExtra("nombreContac", contacto.nombreContac);
                            intent.putExtra("telefonoContac", contacto.telefonoContac);
                            intent.putExtra("direccionContac", contacto.direccionContac);
                            intent.putExtra("emailContac", contacto.emailContac);

                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }
}