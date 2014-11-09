package com.grupo10.jatrikandroid;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class ListadoPartidos extends Activity {

	public final static String MESSAGE_DATOS = "com.grupo10.jatrikandroid.DATOS";
	public final static String MESSAGE_PARTIDO = "com.grupo10.jatrikandroid.PARTIDO";
	
	private String datos;
	private ListView lista; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listado_partidos);
		
		Intent intent = getIntent();
	    datos = intent.getStringExtra(MainActivity.MESSAGE_DATOS);	
		Gson g = new Gson();
		DataListaPartido dlp = g.fromJson(datos, DataListaPartido.class);

		Log.v("[LOG VERBOSE] --" ,datos);
		
		lista = (ListView) findViewById(R.id.listado_partidos);
        lista.setAdapter(new Lista_adaptador(this, R.layout.activity_objeto_lista, dlp.getLstPartidos()){
			@Override
			public void onEntrada(Object entrada, View view) {
				DataResumenPartido dr = (DataResumenPartido) entrada;
				if (dr != null) {
		            TextView texto_fecha = (TextView) view.findViewById(R.id.texto_fecha); 
		            if (texto_fecha != null) 
		            	texto_fecha.setText(dr.getFecha()); 
		              
		            TextView texto_local = (TextView) view.findViewById(R.id.texto_local); 
		            if (texto_local != null)
		            	texto_local.setText(dr.getEqLocal()); 
		            
		            TextView texto_resultado_local = (TextView) view.findViewById(R.id.texto_resultado_local); 
		            if (texto_resultado_local != null)
		            	texto_resultado_local.setText(dr.golesEquipoLocal.toString()); 
		            
	            	TextView texto_resultado_visitante = (TextView) view.findViewById(R.id.texto_resultado_visitante); 
		            if (texto_resultado_visitante != null)
		            	texto_resultado_visitante.setText(dr.golesEquipoVisitante.toString()); 	
		            	
		            TextView texto_visitante = (TextView) view.findViewById(R.id.texto_visitante); 
		            if (texto_visitante != null)
		            	texto_visitante.setText(dr.getEqVisitante()); 

		        }	
			}
		});
        
        lista.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
				DataResumenPartido elegido = (DataResumenPartido) pariente.getItemAtPosition(posicion); 
                
                CharSequence texto = "Seleccionado: " + elegido.nomPartido;
                Toast toast = Toast.makeText(ListadoPartidos.this, texto, Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(ListadoPartidos.this, Estadisticas.class);
                intent.putExtra(MESSAGE_DATOS, datos);
                intent.putExtra(MESSAGE_PARTIDO, elegido.nomPartido);
		        startActivity(intent);
			}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listado_partidos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
