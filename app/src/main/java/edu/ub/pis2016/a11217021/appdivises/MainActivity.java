package edu.ub.pis2016.a11217021.appdivises;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText txtDinero;
    TextView txtTipoEntrada;
    Spinner listEntrada;
    Spinner listSalida;
    Button btnConvertir;
    TextView txtResultado;
    TextView txtTipoSalida;

    TextView txtPorcentajeComision;
    EditText eTxtPorcentajeComision;

    TextView txtTipoCambio;
    EditText eTxtTipoCambio;

    double porcentajeComision;
    double porcentajeCambio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarMenu();
        // ejem

    }

    private void inicializarMenu() {

        inicializarPanel2();
        inicializarPanel1();
        inicializarPanel4();
        inicializarPanel3();
        inicializarPanelPorcentajeComision();
        inicializarPanelTipoCambio();
    }

    private void inicializarPanelTipoCambio(){
        this.porcentajeCambio = 5;
        this.porcentajeComision = 0;

        this.eTxtTipoCambio = (EditText) findViewById(R.id.eTxtTipoCambio);
        this.txtTipoCambio = (TextView) findViewById(R.id.txtTipoCambio);
        this.txtTipoCambio.setText(getResources().getString(R.string.tipoCambio));
        this.eTxtTipoCambio.setText(String.valueOf(this.porcentajeCambio));
        this.eTxtTipoCambio.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                porcentajeCambio = Double.valueOf(eTxtTipoCambio.getText().toString());
                actualizarCambioMoneda();
                actualizarResultado();
                return false;
            }
        });
    }

    private void actualizarCambioMoneda(){
        if(this.listEntrada.getSelectedItemPosition() !=  this.listSalida.getSelectedItemPosition()){
            this.eTxtTipoCambio.setEnabled(true);
            if(this.listEntrada.getSelectedItemPosition() == 0 ){
                this.eTxtTipoCambio.setText(String.valueOf(this.porcentajeCambio));
            }else {
                this.eTxtTipoCambio.setText(String.valueOf(-this.porcentajeCambio));
            }
        }else{
            this.eTxtTipoCambio.setText(String.valueOf(0));
            this.eTxtTipoCambio.setEnabled(false);
        }
    }

    private void inicializarPanelPorcentajeComision() {
        this.porcentajeComision = 0;
        this.txtPorcentajeComision = (TextView) findViewById(R.id.txtPorcentajeComision);
        this.txtPorcentajeComision.setText(getResources().getString(R.string.porcentajeComision));
        this.eTxtPorcentajeComision = (EditText) findViewById(R.id.eTxtPorcentajeComision);
        this.eTxtPorcentajeComision.setText(String.valueOf(this.porcentajeComision));

        this.eTxtPorcentajeComision.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                actualizarPorcentaje();
                actualizarResultado();
                return false;
            }
        });
    }


    private void inicializarPanel1() {
        this.txtDinero = (EditText) findViewById(R.id.txtDinero);

        this.txtDinero.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                actualizarResultado();
                return false;
            }
        });

        this.txtTipoEntrada = (TextView) findViewById(R.id.txtTipoEntrada);
        this.txtTipoEntrada.setText(getResources().getString(R.string.euros));
    }

    private void inicializarPanel2() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.listaTipoMonedas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.listEntrada = (Spinner) findViewById(R.id.listEntrada);
        this.listEntrada.setAdapter(adapter);
        this.listEntrada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence texto;
                txtResultado.setText("");
                if(position == 0){
                    texto = getResources().getString(R.string.euros);
                }else{
                    texto = getResources().getString(R.string.dolares);
                }
                //CharSequence texto = (CharSequence) parent.getSelectedItem();                 //preguntar si esto es correcto
                txtTipoEntrada.setText(texto);
                actualizarCambioMoneda();
                actualizarResultado();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.listSalida = (Spinner) findViewById(R.id.listSalida);
        this.listSalida.setAdapter(adapter);
        this.listSalida.setSelection(1);
        this.listSalida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence texto;
                txtResultado.setText("");
                if(position == 0){
                    texto = getResources().getString(R.string.euros);
                }else{
                    texto = getResources().getString(R.string.dolares);
                }

                txtTipoSalida.setText(texto);
                actualizarCambioMoneda();
                actualizarResultado();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void actualizarPorcentaje() {
        this.porcentajeComision = Double.valueOf(this.eTxtPorcentajeComision.getText().toString());
    }

    private void inicializarPanel3() {

        this.btnConvertir = (Button) findViewById(R.id.btnConvertir);
        this.btnConvertir.setText(getResources().getString(R.string.convertir));

        this.btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarResultado();
            }
        });
    }

    private void inicializarPanel4() {
        this.txtResultado = (TextView) findViewById(R.id.txtResultado);
        CharSequence resultado = "0";
        this.txtResultado.setText(resultado);
        this.txtTipoSalida = (TextView) findViewById(R.id.txtTipoSalida);
        this.txtTipoSalida.setText(getResources().getString(R.string.euros));
    }

    private void actualizarResultado() {
        if(!txtTipoEntrada.getText().equals(txtTipoSalida.getText())){
            try{
                double x = Double.valueOf(txtDinero.getText().toString());
                double resultado;
                double comision = this.porcentajeComision/100;
                double cambio;
                double calculo;
                CharSequence texto;
                if(txtTipoEntrada.getText().equals("euros")){
                    cambio  = this.porcentajeCambio/100;
                }else{
                    cambio  = -this.porcentajeCambio/100;
                }
                calculo =  (x + (x*cambio));
                resultado = calculo - (calculo*comision);
                texto = String.valueOf(resultado);
                txtResultado.setText(texto);
            }catch(NumberFormatException nfe){
                txtResultado.setText(txtDinero.getText());
            }
        }else{
            try{
                double x = Double.valueOf(txtDinero.getText().toString());
                double resultado;
                double comision = this.porcentajeComision/100;
                resultado = x -(x*comision);
                CharSequence texto;
                texto = String.valueOf(resultado);
                txtResultado.setText(texto);
            }catch(NumberFormatException nfe){
                txtResultado.setText(txtDinero.getText());
            }

        }

    }

}

