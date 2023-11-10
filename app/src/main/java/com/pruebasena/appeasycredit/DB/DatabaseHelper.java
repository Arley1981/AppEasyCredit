package com.pruebasena.appeasycredit.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Nombre base de datos
    private static final String DATABASE_NAME = "Easy_Credit.db";
    private static final int DATABASE_VERSION = 1;
    //Tabla Usuario
    public static final String TABLE_USUARIOS = "usuario";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ROL = "rol";
    public static final String COLUMN_NOMBRES = "nombres";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String  COLUMN_IDENTIFICACION = "identificacion";
    public static final String COLUMN_CONTRASEÑA = "contraseña";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_DIRECCION = "direccion";
    public static final String COLUMN_EDAD = "edad";

    private static final String DATABASE_CREATE_USUARIOS = "create table "
            + TABLE_USUARIOS + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_ROL + " text not null, "
            + COLUMN_NOMBRES + " text not null, "
            + COLUMN_APELLIDOS + " text not null, "
            + COLUMN_IDENTIFICACION + " text not null unique, "
            + COLUMN_CONTRASEÑA + " text not null, "
            + COLUMN_TELEFONO + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_DIRECCION + " text not null, "
            + COLUMN_EDAD + " integer not null);";

    //Tabla Cliente
    public static final String TABLE_CLIENTES = "cliente";
    public static final String COLUMN_ID_CLIENTE = "id";
    public static final String COLUMN_TIPO_IDENTIFICACION = "tipo_identificacion";
    public static final String  COLUMN_IDENTIFICACION_CLIENTE = "identificacion";
    public static final String COLUMN_NOMBRES_CLIENTE = "nombres";
    public static final String COLUMN_APELLIDOS_CLIENTE = "apellidos";
    public static final String COLUMN_CONTRASEÑA_CLIENTE = "contraseña";
    public static final String COLUMN_TELEFONO_CLIENTE = "telefono";
    public static final String COLUMN_EMAIL_CLIENTE = "email";
    public static final String COLUMN_DIRECCION_CLIENTE = "direccion";
    public static final String COLUMN_EDAD_CLIENTE = "edad";

    private static final String DATABASE_CREATE_CLIENTES = "create table "
            + TABLE_CLIENTES + "(" + COLUMN_ID_CLIENTE
            + " integer primary key autoincrement, "
            + COLUMN_TIPO_IDENTIFICACION + " text not null, "
            + COLUMN_IDENTIFICACION_CLIENTE + " text not null unique, "
            + COLUMN_NOMBRES_CLIENTE + " text not null, "
            + COLUMN_APELLIDOS_CLIENTE + " text not null, "
            + COLUMN_CONTRASEÑA_CLIENTE + " text not null, "
            + COLUMN_TELEFONO_CLIENTE + " text not null, "
            + COLUMN_EMAIL_CLIENTE + " text not null, "
            + COLUMN_DIRECCION_CLIENTE + " text not null, "
            + COLUMN_EDAD_CLIENTE + " integer not null);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla de usuarios
        db.execSQL(DATABASE_CREATE_USUARIOS);

        // Crea la tabla de clientes
        db.execSQL(DATABASE_CREATE_CLIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        onCreate(db);
    }

}
