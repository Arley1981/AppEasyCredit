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
import com.pruebasena.appeasycredit.controller.RegistrarClienteController;
import com.pruebasena.appeasycredit.model.ClienteModel;


public class ClienteActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    private Spinner spnIdentificacion;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etIdentificacion;
    private EditText etContraseña;
    private EditText etTelefono;
    private EditText etEmail;
    private EditText etDireccion;
    private EditText etEdad;
    private Button btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar;

    private RegistrarClienteController registrarClienteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        dbHelper = new DatabaseHelper(this);

        spnIdentificacion = findViewById(R.id.spnIdentificacion);
        etIdentificacion = findViewById(R.id.etIdentificacion);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
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

        registrarClienteController = new RegistrarClienteController(this);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCliente();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarCliente();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarCliente();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarCliente();
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarCampos();
            }
        });
    }

    private void registrarCliente() {
        ClienteModel cliente = new ClienteModel(
                spnIdentificacion.getSelectedItem().toString(),
                etIdentificacion.getText().toString(),
                etNombres.getText().toString(),
                etApellidos.getText().toString(),
                etContraseña.getText().toString(),
                etTelefono.getText().toString(),
                etEmail.getText().toString(),
                etDireccion.getText().toString(),
                Integer.parseInt(etEdad.getText().toString())
        );

        boolean creadoExitosamente = registrarClienteController.registrarCliente(cliente);

        if (creadoExitosamente) {
            Toast.makeText(this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al registrar cliente", Toast.LENGTH_SHORT).show();
        }
    }

    // Función consultar un cliente
    private void consultarCliente() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_TIPO_IDENTIFICACION,
                DatabaseHelper.COLUMN_IDENTIFICACION_CLIENTE,
                DatabaseHelper.COLUMN_NOMBRES_CLIENTE,
                DatabaseHelper.COLUMN_APELLIDOS_CLIENTE,
                DatabaseHelper.COLUMN_CONTRASEÑA_CLIENTE,
                DatabaseHelper.COLUMN_TELEFONO_CLIENTE,
                DatabaseHelper.COLUMN_EMAIL_CLIENTE,
                DatabaseHelper.COLUMN_DIRECCION_CLIENTE,
                DatabaseHelper.COLUMN_EDAD_CLIENTE
        };

        String selection = DatabaseHelper.COLUMN_IDENTIFICACION_CLIENTE + " = ?";
        String[] selectionArgs = { etIdentificacion.getText().toString() };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_CLIENTES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String tipo_identificacion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIPO_IDENTIFICACION));
            int tipo_identificacionIndex = getIndexForSpinnerValue(spnIdentificacion, tipo_identificacion);
            spnIdentificacion.setSelection(tipo_identificacionIndex);

            etIdentificacion.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IDENTIFICACION_CLIENTE)));
            etNombres.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRES_CLIENTE)));
            etApellidos.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDOS_CLIENTE)));
            etContraseña.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTRASEÑA_CLIENTE)));
            etTelefono.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEFONO_CLIENTE)));
            etEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_CLIENTE)));
            etDireccion.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIRECCION_CLIENTE)));
            etEdad.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EDAD_CLIENTE))));
        } else {
            Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show();
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

    //Función Actualizar un Cliente
    private void actualizarCliente() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TIPO_IDENTIFICACION, spnIdentificacion.getSelectedItem().toString());
        values.put(DatabaseHelper.COLUMN_IDENTIFICACION_CLIENTE, etIdentificacion.getText().toString());
        values.put(DatabaseHelper.COLUMN_NOMBRES_CLIENTE, etNombres.getText().toString());
        values.put(DatabaseHelper.COLUMN_APELLIDOS_CLIENTE, etApellidos.getText().toString());
        values.put(DatabaseHelper.COLUMN_CONTRASEÑA_CLIENTE, etContraseña.getText().toString());
        values.put(DatabaseHelper.COLUMN_TELEFONO_CLIENTE, etTelefono.getText().toString());
        values.put(DatabaseHelper.COLUMN_EMAIL_CLIENTE, etEmail.getText().toString());
        values.put(DatabaseHelper.COLUMN_DIRECCION_CLIENTE, etDireccion.getText().toString());
        values.put(DatabaseHelper.COLUMN_EDAD_CLIENTE, Integer.parseInt(etEdad.getText().toString()));

        String selection = DatabaseHelper.COLUMN_IDENTIFICACION_CLIENTE + " = ?";
        String[] selectionArgs = { etIdentificacion.getText().toString() };

        int count = db.update(
                DatabaseHelper.TABLE_CLIENTES,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(this, "Cliente actualizado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al actualizar cliente", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    // función Eliminar Cliente
    private void eliminarCliente() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Eliminación")
                .setMessage("¿Está seguro que quiere eliminar el Cliente?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        String selection = DatabaseHelper.COLUMN_IDENTIFICACION_CLIENTE + " = ?";
                        String[] selectionArgs = { etIdentificacion.getText().toString() };

                        int count = db.delete(
                                DatabaseHelper.TABLE_CLIENTES,
                                selection,
                                selectionArgs
                        );

                        if (count > 0) {
                            Toast.makeText(ClienteActivity.this, "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        } else {
                            Toast.makeText(ClienteActivity.this, "Error al eliminar cliente", Toast.LENGTH_SHORT).show();
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
        spnIdentificacion.setSelection(0);
        etIdentificacion.setText("");
        etNombres.setText("");
        etApellidos.setText("");
        etContraseña.setText("");
        etTelefono.setText("");
        etEmail.setText("");
        etDireccion.setText("");
        etEdad.setText("");
    }
}
