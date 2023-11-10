package com.pruebasena.appeasycredit.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pruebasena.appeasycredit.DB.DatabaseHelper;
import com.pruebasena.appeasycredit.model.ClienteModel;

public class RegistrarClienteController {

    private Context context;
    private DatabaseHelper dbHelper;

    public RegistrarClienteController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public boolean registrarCliente(ClienteModel cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TIPO_IDENTIFICACION, cliente.getTipoIdentificacion());
        values.put(DatabaseHelper.COLUMN_NOMBRES_CLIENTE, cliente.getNombres());
        values.put(DatabaseHelper.COLUMN_APELLIDOS_CLIENTE, cliente.getApellidos());
        values.put(DatabaseHelper.COLUMN_IDENTIFICACION_CLIENTE, cliente.getIdentificacion());
        values.put(DatabaseHelper.COLUMN_CONTRASEÑA_CLIENTE, cliente.getContraseña());
        values.put(DatabaseHelper.COLUMN_TELEFONO_CLIENTE, cliente.getTelefono());
        values.put(DatabaseHelper.COLUMN_EMAIL_CLIENTE, cliente.getEmail());
        values.put(DatabaseHelper.COLUMN_DIRECCION_CLIENTE, cliente.getDireccion());
        values.put(DatabaseHelper.COLUMN_EDAD_CLIENTE, cliente.getEdad());

        long newRowId = db.insert(DatabaseHelper.TABLE_CLIENTES, null, values);

        db.close();

        return newRowId != -1;
    }
}
