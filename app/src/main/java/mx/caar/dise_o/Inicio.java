package mx.caar.dise_o;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Inicio extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    AnimationDrawable animImagenes = new AnimationDrawable();
    ImageView imgCarrusel;
    ActionBar nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        imgCarrusel = (ImageView) findViewById(R.id.imgSlider);
        nav_bar = getSupportActionBar();
        BottomNavigationView nav_bar = (BottomNavigationView) findViewById(R.id.navBar);

        nav_bar.setOnNavigationItemSelectedListener(this);

        nav_bar.setSelectedItemId(R.id.nav_inicio);
        nav_bar.setSelected(true);

        mostrar_Imagenes();

    }


    public void enviar_subcategoria(View view){
        String nombre_categoria = "";
        switch (view.getId()){
            case R.id.btnComida:
                nombre_categoria = "Comida";
                break;
            case R.id.btnDomestico:
                nombre_categoria = "Domesticos";
                break;
            case R.id.btnHerramientas:
                nombre_categoria = "Herramientas";
                break;
            case R.id.btnJuegos:
                nombre_categoria = "Videojuegos";
                break;
            case R.id.btnRopa:
                nombre_categoria = "Ropa";
                break;
            case R.id.btnTecnologia:
                nombre_categoria = "Tecnologia";
                break;
        }

        Intent subcategoria = new Intent(Inicio.this, SubCategorias.class);
        subcategoria.putExtra("CATEGORIA", nombre_categoria);
        startActivity(subcategoria);

    }


    public void mostrar_Imagenes(){
        animImagenes.addFrame(getResources().getDrawable(R.drawable.blusa_mujer), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.falda_jean), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.gear_galaxy), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.iphonex), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.lavadora), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.licuadora), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.p20), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.plancha), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.refri), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.reloj_mujer), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.samsungs9), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.sudadera_hombre), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.comida_saludable), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.sueter), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.tacos), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.traje_completo), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.anvorgesa), 3000);
        animImagenes.addFrame(getResources().getDrawable(R.drawable.laptop), 3000);

        imgCarrusel.setImageDrawable(animImagenes);

        animImagenes.start();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_carrito:
                Intent send_Carrito = new Intent(this, Carrito.class );
                startActivity(send_Carrito);
                break;
            case R.id.nav_perfil:
                Intent send_Perfil = new Intent(this, Perfil.class );
                startActivity(send_Perfil);
                break;
        }
        return false;
    }
}
