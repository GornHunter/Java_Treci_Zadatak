package uns.ftn.deet.kel.moviesdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import uns.ftn.deet.kel.moviesdatabase.sqlite.helper.DatabaseHelper;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Actor;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Director;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Movie;

public class MainActivity extends AppCompatActivity {
    // Database Helper
    DatabaseHelper databaseHelper;

    EditText etImeGlumca;
    EditText etDatumGlumca;

    EditText etImeReditelja;
    EditText etDatumReditelja;

    EditText etImeFilma;
    EditText etDatumFilma;

    Spinner spnActors;
    Spinner spnDirectors;
    Spinner spnMovies;
    Spinner spnIzborReditelja;

    Button btnDodajGlumca;
    Button btnAzurirajGLumca;
    Button btnIzbrisiGlumca;

    Button btnDodajReditelja;
    Button btnAzurirajReditelja;
    Button btnIzbrisiReditelja;

    Button btnDodajFilm;
    Button btnAzurirajFilm;
    Button btnIzbrisiFilm;

    Button btnDeleteDatabase;
    Button btnFindActors;
    Button btnRestoreDatabase;

    List<Actor> la;
    List<Director> ld;
    List<Movie> mv;

    Actor glumac;
    Director reditelj;
    Movie film;

    @Override
    protected void onStop() {
        super.onStop();
        databaseHelper.closeDB();
    }

    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etImeGlumca = (EditText) findViewById(R.id.etGlumacIme);
        etDatumGlumca = (EditText) findViewById(R.id.etGlumacDatumRodjenja);

        etImeReditelja = (EditText) findViewById(R.id.etRediteljIme);
        etDatumReditelja = (EditText) findViewById(R.id.etRediteljDatumRodjenja);

        etImeFilma = (EditText) findViewById(R.id.etFilmIme);
        etDatumFilma = (EditText) findViewById(R.id.etDatumIzlaskaFilma);

        spnActors = (Spinner) findViewById(R.id.spnActors);
        spnDirectors = (Spinner) findViewById(R.id.spnDirectors);
        spnMovies = (Spinner) findViewById(R.id.spnMovies);
        spnIzborReditelja = (Spinner) findViewById(R.id.spnReditelji);

        btnDodajGlumca = (Button) findViewById(R.id.btnGlumac);
        btnAzurirajGLumca = (Button) findViewById(R.id.btnAzurirajGlumca);
        btnIzbrisiGlumca = (Button) findViewById(R.id.btnIzbrisiGlumca);

        btnDodajReditelja = (Button) findViewById(R.id.btnReditelj);
        btnAzurirajReditelja = (Button) findViewById(R.id.btnAzurirajReditelja);
        btnIzbrisiReditelja = (Button) findViewById(R.id.btnIzbrisiReditelja);

        btnDodajFilm = (Button) findViewById(R.id.btnFilm);
        btnAzurirajFilm = (Button) findViewById(R.id.btnAzurirajFilm);
        btnIzbrisiFilm = (Button) findViewById(R.id.btnIzbrisiFilm);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.createTables();


        if(databaseHelper.getAllActors().size() != 0){
            la = databaseHelper.getAllActors();
            loadSpinnerDataActors((ArrayList<Actor>) la);
        }

        if(databaseHelper.getAllDirectors().size() != 0){
            ld = databaseHelper.getAllDirectors();
            loadSpinnerDataDirectors((ArrayList<Director>) ld);
        }

        if(databaseHelper.getAllMovies().size() != 0){
            mv = databaseHelper.getAllMovies();
            loadSpinnerDataMovies((ArrayList<Movie>) mv);
        }


        btnDodajGlumca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etImeGlumca.getText().toString().equals("") && !etDatumGlumca.getText().toString().equals("")) {
                    databaseHelper.createActor(new Actor(etImeGlumca.getText().toString(), etDatumGlumca.getText().toString()));
                    la = databaseHelper.getAllActors();
                    loadSpinnerDataActors((ArrayList<Actor>) la);
                    //etImeGlumca.setText("");
                    //etDatumGlumca.setText("");
                }
            }
        });

        btnAzurirajGLumca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glumac.setName(etImeGlumca.getText().toString());
                glumac.setBirthDate(etDatumGlumca.getText().toString());
                databaseHelper.updateActor(glumac);
                la = databaseHelper.getAllActors();
                loadSpinnerDataActors((ArrayList<Actor>) la);
                etImeGlumca.setText("");
                etDatumGlumca.setText("");
            }
        });

        btnIzbrisiGlumca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteActor(glumac.getId());
                la = databaseHelper.getAllActors();
                loadSpinnerDataActors((ArrayList<Actor>) la);
                etImeGlumca.setText("");
                etDatumGlumca.setText("");
            }
        });

        spnActors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                glumac = la.get(position);
                etImeGlumca.setText(glumac.getName());
                etDatumGlumca.setText(glumac.getBirthDate());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDodajReditelja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etImeReditelja.getText().toString().equals("") && !etDatumReditelja.getText().toString().equals("")) {
                    databaseHelper.createDirector(new Director(etImeReditelja.getText().toString(), etDatumReditelja.getText().toString()));
                    ld = databaseHelper.getAllDirectors();
                    loadSpinnerDataDirectors((ArrayList<Director>) ld);
                }
            }
        });

        btnAzurirajReditelja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reditelj.setName(etImeReditelja.getText().toString());
                reditelj.setBirthDate(etDatumReditelja.getText().toString());
                databaseHelper.updateDirector(reditelj);
                ld = databaseHelper.getAllDirectors();
                loadSpinnerDataDirectors((ArrayList<Director>) ld);
                etImeReditelja.setText("");
                etDatumReditelja.setText("");
            }
        });

        btnIzbrisiReditelja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteDirector(reditelj.getId());
                ld = databaseHelper.getAllDirectors();
                loadSpinnerDataDirectors((ArrayList<Director>) ld);
                etImeReditelja.setText("");
                etDatumReditelja.setText("");
            }
        });

        spnDirectors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reditelj = ld.get(position);
                etImeReditelja.setText(reditelj.getName());
                etDatumReditelja.setText(reditelj.getBirthDate());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*btnDeleteDatabase = (Button) findViewById(R.id.btnDeleteDatabase);
        btnDeleteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHelper != null) {
                    databaseHelper.dropTables();
                    loadSpinnerDataActors(new ArrayList<Actor>());
                    loadSpinnerDataDirectors(new ArrayList<Director>());
                    loadSpinnerDataMovies(new ArrayList<Movie>());
                }

            }
        });*/

        /*btnFindActors = (Button) findViewById(R.id.btnActorsInMovie);
        btnFindActors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spnMovies.getSelectedItem() != null) {
                    String movie = spnMovies.getSelectedItem().toString();
                    List<Actor> lst = databaseHelper.getAllActorsInMovie(movie);
                    loadSpinnerDataActors((ArrayList<Actor>) lst);
                }
            }
        });*/

        /*btnRestoreDatabase = (Button) findViewById(R.id.btnRestoreDatabase);
        btnRestoreDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTablesAndInitData();
            }
        });*/

        //databaseHelper = new DatabaseHelper(getApplicationContext());

        //createTablesAndInitData();
    }

    void createTablesAndInitData(){
        databaseHelper.createTables();

        if (databaseHelper.getAllActors().size() == 0) {
            Actor a1 = new Actor("Brad Pitt", "18-12-1963");
            Actor a2 = new Actor("Edward Norton", "18-08-1969");
            Actor a3 = new Actor("Samuel L. Jackson", "21-12-1948");
            Actor a4 = new Actor("Bruce Willis", "19-03-1955");
            Director d1 = new Director("Quentin Tarantino", "27-03-1963");
            Director d2 = new Director("Martin Scorsese", "17-11-1942");
            Director d3 = new Director("David Fincher", "28-08-1962");

            databaseHelper.createActor(a1);
            databaseHelper.createActor(a2);
            databaseHelper.createActor(a3);
            databaseHelper.createActor(a4);
            databaseHelper.createDirector(d1);
            databaseHelper.createDirector(d2);
            databaseHelper.createDirector(d3);
            Movie m1 = new Movie("Pulp fiction", "23-09-1994");
            m1.setDirector(d1);
            databaseHelper.createMovie(m1);
            m1.addActor(a4);
            m1.addActor(a3);
            databaseHelper.addActorsInMovie(m1);
            Movie m2 = new Movie("Fight club", "15-10-1999");
            m2.setDirector(d3);
            databaseHelper.createMovie(m2);
            m2.addActor(a1);
            m2.addActor(a2);
            databaseHelper.addActorsInMovie(m2);
        }

        List<Actor> la = databaseHelper.getAllActors();
        loadSpinnerDataActors((ArrayList<Actor>) la);
        List<Director> ld = databaseHelper.getAllDirectors();
        loadSpinnerDataDirectors((ArrayList<Director>) ld);
        List<Movie> mv = databaseHelper.getAllMovies();
        loadSpinnerDataMovies((ArrayList<Movie>) mv);
    }

    void loadSpinnerDataActors (ArrayList<Actor> al){
        ArrayList<String> actornames = new ArrayList<>();
        for (Actor actor : al){
            actornames.add(actor.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, actornames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnActors.setAdapter(dataAdapter);
    }

    void loadSpinnerDataDirectors (ArrayList<Director> al){
        ArrayList<String> directornames = new ArrayList<>();
        for (Director director : al){
            directornames.add(director.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, directornames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnDirectors.setAdapter(dataAdapter);
    }

    void loadSpinnerDataMovies (ArrayList<Movie> al){
        ArrayList<String> movienames = new ArrayList<>();
        for (Movie movie : al){
            movienames.add(movie.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, movienames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnMovies.setAdapter(dataAdapter);
    }
}