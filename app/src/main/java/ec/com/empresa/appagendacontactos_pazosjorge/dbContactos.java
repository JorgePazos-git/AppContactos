package ec.com.empresa.appagendacontactos_pazosjorge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class dbContactos {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    static int idCont = 0;

    public dbContactos(Context contexto, String dbName, int version) {
        dbHelper = new DBHelper(contexto, dbName, null, version);
    }

    public Contacto Insert(String nombreContac, String telefonoContac, String direccionContac, String emailContac){

        long qty = 0;

        try{
            db = dbHelper.getWritableDatabase();
            ContentValues row = new ContentValues();
            row.put("nombreContac", nombreContac);
            row.put("telefonoContac", telefonoContac);
            row.put("direccionContac", direccionContac);
            row.put("emailContac", emailContac);

            qty = db.insert("contactos", null, row);
        }catch (Exception ex){
            ex.toString();
        }

        if(qty > 0){
            idCont++;
            Contacto data = new Contacto();
            data.idContac = idCont;
            data.nombreContac = nombreContac;
            data.telefonoContac = telefonoContac;
            data.direccionContac = direccionContac;
            data.emailContac = emailContac;

            return data;
        }else{
            return null;
        }
    }

    public Contacto Read_One(int idContac){
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT idContac, nombreContac, telefonoContac, direccionContac, emailContac FROM contactos where idContac = '" + idContac + "'", null);

        if(cursor.getCount() > 0){
            Contacto contacto;
            cursor.moveToNext();

            contacto = new Contacto();
            contacto.idContac = cursor.getInt(0);
            contacto.nombreContac = cursor.getString(1);
            contacto.telefonoContac = cursor.getString(2);
            contacto.direccionContac = cursor.getString(3);
            contacto.emailContac = cursor.getString(4);

            return contacto;
        }else{
            return null;
        }
    }

    public Contacto[] Read_All(){
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT idContac, nombreContac, telefonoContac, direccionContac, emailContac FROM contactos Order by nombreContac", null);

        if(cursor.getCount() > 0){
            Contacto[] datos = new Contacto[cursor.getCount()];
            Contacto contacto;
            int i = 0;

            while(cursor.moveToNext()){
                contacto = new Contacto();
                contacto.idContac = cursor.getInt(0);
                contacto.nombreContac = cursor.getString(1);
                contacto.telefonoContac = cursor.getString(2);
                contacto.direccionContac = cursor.getString(3);
                contacto.emailContac = cursor.getString(4);
                datos[i++] = contacto;
            }
            return datos;
        }else{
            return null;
        }

    }

    public boolean Update(int idContac, String nombreContac, String telefonoContac, String direccionContac, String emailContac){
        db = dbHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("nombreContac", nombreContac);
        row.put("telefonoContac", telefonoContac);
        row.put("direccionContac", direccionContac);
        row.put("emailContac", emailContac);

        long qty = db.update("contactos", row, "idContac='" + idContac + "'", null);

        return qty>0;

    }

    public boolean Delete(int idContac){
        db = dbHelper.getWritableDatabase();
        long qty = db.delete("contactos", "idContac='" + idContac + "'", null);

        return qty>0;
    }
}
