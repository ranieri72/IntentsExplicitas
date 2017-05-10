package com.ranieri.exercicio03;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    AdapterView.OnItemClickListener tradadorDeEventos = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            tratarClique(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        String [] opcoes = new String[]{"Internet", "Discar", "Ligar", "Mapa", "Compartilhar", "Personalizada"};

        ListView listView = new ListView(this);
        setContentView(listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcoes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(tradadorDeEventos);
    }

    private void tratarClique(int position) {
        Intent it = null;

        switch (position){
            case 0:
                it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(it);
                break;
            case 1:
                discar();
                break;
            case 2:
                ligar();
                break;
            case 3:
                mapa();
                break;
            case 4:
                compartilhar();
                break;
            case 5:
                personalizada();
                break;
        }
    }

    private void personalizada() {
        Intent it = new Intent("TA_FAVORAVEL", Uri.parse("Ranieri:teste"));
        startActivity(it);
    }

    private void discar() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:983022787")));
    }

    private void ligar() {
        if(temPermissao()) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:983022787")));
        } else {
            solicitarPermissao();
        }
    }

    private void mapa() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Unibratec")));
    }

    private void compartilhar() {
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, "Texto Compartilhado");
        it.setType("text/plain");
        startActivity(it);
    }

    private void solicitarPermissao() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
    }

    private boolean temPermissao() {

        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ligar();
        }
    }
}
