package com.pruebasena.appeasycredit.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.database.Cursor;

import com.pruebasena.appeasycredit.DB.DatabaseHelper;
import com.pruebasena.appeasycredit.R;
import com.pruebasena.appeasycredit.controller.CrearUsuarioController;
import com.pruebasena.appeasycredit.model.UsuarioModel;


public class UsuarioActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    private Spinner spnRol;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etIdentificacion;
    private EditText etContraseña;
    private EditText etTelefono;
    private EditText etEmail;
    private EditText etDireccion;
    private EditText etEdad;
    private Button btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar;

    private CrearUsuarioController crearUsuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        spnRol = findViewById(R.id.spnRol);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etIdentificacion = findViewById(R.id.etIdentificacion);
        etContraseña = findViewById(R.id.etContraseña);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        etDireccion = findViewById(R.id.etDireccion);
        etEdad = findViewById(R.id.etEdad);

        btnCrear = findViewById(R.id.btnCrear);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnLimpiar = findViewById(R.id.btnLimpiar);

        crearUsuarioController = new CrearUsuarioController(this);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearUsuario();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarUsuario();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarUsuario();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarUsuario();
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarCampos();
            }
        });
    }

    private void crearUsuario() {
        UsuarioModel usuario = new UsuarioModel(
                spnRol.getSelectedItem().toString(),
                etNombres.getText().toString(),
                etApellidos.getText().toString(),
                etIdentificacion.getText().toString(),
                etContraseña.getText().toString(),
                etTelefono.getText().toString(),
                etEmail.getText().toString(),
                etDireccion.getText().toString(),
                Integer.parseInt(etEdad.getText().toString())
        );

        boolean creadoExitosamente = crearUsuarioController.crearUsuario(usuario);

        if (creadoExitosamente) {
            Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al crear usuario", Toast.LENGTH_SHORT).show();
        }
    }

    // Función consultar un usuariio
    private void consultarUsuario() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_ROL,
                DatabaseHelper.COLUMN_NOMBRES,
                DatabaseHelper.COLUMN_APELLIDOS,
                DatabaseHelper.COLUMN_IDENTIFICACION,
                DatabaseHelper.COLUMN_CONTRASEÑA,
                DatabaseHelper.COLUMN_TELEFONO,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_DIRECCION,
                DatabaseHelper.COLUMN_EDAD
        };

        String selection = DatabaseHelper.COLUMN_IDENTIFICACION + " = ?";
        String[] selectionArgs = { etIdentificacion.getText().toString() };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USUARIOS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String rol = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROL));
            int rolIndex = getIndexForSpinnerValue(spnRol, rol);
            spnRol.setSelection(rolIndex);

            etNombres.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRES)));
            etApellidos.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDOS)));
            etIdentificacion.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IDENTIFICACION)));
            etContraseña.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTRASEÑA)));
            etTelefono.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEFONO)));
            etEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)));
            etDireccion.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIRECCION)));
            etEdad.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EDAD))));
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }

    private int getIndexForSpinnerValue(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                return i;
            }
        }
        return 0; // O retorna el índice que desees si el valor no se encuentra
    }

        //Función Actualizar un Usuario
    private void actualizarUsuario() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ROL, spnRol.getSelectedItem().toString());
        values.put(DatabaseHelper.COLUMN_NOMBRES, etNombres.getText().toString());
        values.put(DatabaseHelper.COLUMN_APELLIDOS, etApellidos.getText().toString());
        values.put(DatabaseHelper.COLUMN_IDENTIFICACION, etIdentificacion.getText().toString());
        values.put(DatabaseHelper.COLUMN_CONTRASEÑA, etContraseña.getText().toString());
        values.put(DatabaseHelper.COLUMN_TELEFONO, etTelefono.getText().toString());
        values.put(DatabaseHelper.COLUMN_EMAIL, etEmail.getText().toString());
        values.put(DatabaseHelper.COLUMN_DIRECCION, etDireccion.getText().toString());
        values.put(DatabaseHelper.COLUMN_EDAD, Integer.parseInt(etEdad.getText().toString()));

        String selection = DatabaseHelper.COLUMN_IDENTIFICACION + " = ?";
        String[] selectionArgs = { etIdentificacion.getText().toString() };

        int count = db.update(
                DatabaseHelper.TABLE_USUARIOS,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

        // función Eliminar usuario
        private void eliminarUsuario() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmar Eliminación")
                    .setMessage("¿Está seguro que quiere eliminar el usuario?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            String selection = DatabaseHelper.COLUMN_IDENTIFICACION + " = ?";
                            String[] selectionArgs = { etIdentificacion.getText().toString() };

                            int count = db.delete(
                                    DatabaseHelper.TABLE_USUARIOS,
                                    selection,
                                    selectionArgs
                            );

                            if (count > 0) {
                                Toast.makeText(UsuarioActivity.this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            } else {
                                Toast.makeText(UsuarioActivity.this, "Error al eliminar usuario", Toast.LENGTH_SHORT).show();
                            }

                            db.close();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // No hacer nada si el usuario elige "NO"
                        }
                    })
                    .show();
        }

    //Funcion limpiar campos
    private void limpiarCampos() {
        spnRol.setSelection(0);
        etNombres.setText("");
        etApellidos.setText("");
        etIdentificacion.setText("");
        etContraseña.setText("");
        etTelefono.setText("");
        etEmail.setText("");
        etDireccion.setText("");
        etEdad.setText("");
    }
}