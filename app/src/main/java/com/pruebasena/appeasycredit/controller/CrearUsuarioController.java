package com.pruebasena.appeasycredit.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pruebasena.appeasycredit.DB.DatabaseHelper;
import com.pruebasena.appeasycredit.model.UsuarioModel;

public class CrearUsuarioController {

    private Context context;
    private DatabaseHelper dbHelper;

    public CrearUsuarioController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public boolean crearUsuario(UsuarioModel usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ROL, usuario.getRol());
        values.put(DatabaseHelper.COLUMN_NOMBRES, usuario.getNombres());
        values.put(DatabaseHelper.COLUMN_APELLIDOS, usuario.getApellidos());
        values.put(DatabaseHelper.COLUMN_IDENTIFICACION, usuario.getIdentificacion());
        values.put(DatabaseHelper.COLUMN_CONTRASEÑA, usuario.getContraseña());
        values.put(DatabaseHelper.COLUMN_TELEFONO, usuario.getTelefono());
        values.put(DatabaseHelper.COLUMN_EMAIL, usuario.getEmail());
        values.put(DatabaseHelper.COLUMN_DIRECCION, usuario.getDireccion());
        values.put(DatabaseHelper.COLUMN_EDAD, usuario.getEdad());

        long newRowId = db.insert(DatabaseHelper.TABLE_USUARIOS, null, values);

        db.close();

        return newRowId != -1;
    }
}
