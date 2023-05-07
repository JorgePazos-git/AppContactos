package ec.com.empresa.appagendacontactos_pazosjorge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    dbContactos contactos;
    EditText txtNombreContac, txtTelefonoContac, txtDireccionContac, txtEmailContac;
    Button btnCancelar, btnGuardar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        contactos = new dbContactos(this,"contactos.db", 1);

        txtNombreContac = findViewById(R.id.txtNombreContac);
        txtTelefonoContac = findViewById(R.id.txtTelefonoContac);
        txtDireccionContac = findViewById(R.id.txtDireccionContac);
        txtEmailContac = findViewById(R.id.txtEmailContac);

        Intent intent = getIntent();
        String nombreContac = intent.getStringExtra("nombreContac");
        String telefonoContac = intent.getStringExtra("telefonoContac");
        String direccionContac = intent.getStringExtra("direccionContac");
        String emailContac = intent.getStringExtra("emailContac");

        txtNombreContac.setText(nombreContac);
        txtTelefonoContac.setText(telefonoContac);
        txtDireccionContac.setText(direccionContac);
        txtEmailContac.setText(emailContac);

    }

    public void cmdGuardar_onClick(View v){
        Intent intent = getIntent();
        int idContac = intent.getIntExtra("idContac", 0);
        if(contactos.Read_One(idContac) == null){
            if((txtTelefonoContac.getText().toString()).trim().isEmpty()){
                Toast.makeText(this, "AGREGUE UN NUMERO", Toast.LENGTH_SHORT).show();
                return;
            }

            Contacto c = contactos.Insert(txtNombreContac.getText().toString(), txtTelefonoContac.getText().toString(), txtDireccionContac.getText().toString(),
                    txtEmailContac.getText().toString());

            if(c != null){
                Toast.makeText(this, "CONTACTO GUARDADO", Toast.LENGTH_SHORT).show();
                limpiar();
            }else{
                Toast.makeText(this, "ERROR AL GUARDAR CONTACTO", Toast.LENGTH_SHORT).show();
            }
        }else{
            Boolean c = contactos.Update(idContac,txtNombreContac.getText().toString(),txtTelefonoContac.getText().toString(),txtDireccionContac.getText().toString(),
                    txtEmailContac.getText().toString());

            if(c){
                Toast.makeText(this, "CONTACTO ACTUALIZADO", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "ERROR AL ACTUALIZAR", Toast.LENGTH_SHORT).show();
            }
        }

        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void cmdCancelar_onClick(View v){
        finish();
    }

    public void cmdEliminar_onClick(View v){
        final Dialog popup = new Dialog(this);
        popup.setContentView(R.layout.popup_layout);

        Button btnConfirmar = popup.findViewById(R.id.btnAceptar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int idContac = intent.getIntExtra("idContac", 0);
                Boolean c = contactos.Delete(idContac);

                if(c){
                    Toast.makeText(Activity2.this, "CONTACTO ELIMINADO", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Activity2.this, "ERROR AL ELIMINAR", Toast.LENGTH_SHORT).show();
                }
                popup.dismiss();
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Button btnCancelar = popup.findViewById(R.id.btnCancel);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.show();
    }

    private void limpiar(){
        txtNombreContac.setText("");
        txtTelefonoContac.setText("");
        txtDireccionContac.setText("");
        txtEmailContac.setText("");
    }
}