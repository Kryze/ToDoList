package fr.rath_wuest.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    private static final int ACT_ADD_ITEM = 1;
    private static final String SORTED = "sort";
    private static final String VALTRI="val";
    private static int valtri;
    private SimpleCursorAdapter dataAdapter;
    private ListView lv;
    private boolean sorted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        lv = ((ListView) findViewById(R.id.todolist));

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                createAlertDialog(id);

                return true;


            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                createAlertDialogModif(id);
                //Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();


            }
        });

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        sorted = preferences.getBoolean(SORTED, false);
        valtri = preferences.getInt(VALTRI,valtri);

        displayItems();

    }

    private void displayItems() {
        Cursor cursor;
        cursor = TodoBase.fetchAllItems(this, sorted,valtri);
        dataAdapter = new SimpleCursorAdapter(this, R.layout.row, cursor,
                                              new String [] {TodoBase.KEY_LABEL,TodoBase.KEY_DATE},
                                              new int [] {R.id.label,R.id.date},
                                              0);

        lv.setAdapter(dataAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_sort);
        item.setTitle((sorted) ? R.string.not_sort : R.string.sort);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.db_debug) {
            Intent dbManager = new Intent(this,AndroidDatabaseManager.class);
            startActivity(dbManager);
        }

        if (id == R.id.add_item) {
            Intent addItem = new Intent(this, AddItemActivity.class);
            startActivityForResult(addItem, ACT_ADD_ITEM);
        }

        if (id == R.id.menu_sort) {
            sorted = !sorted;
            if(sorted){
                createAlertDialogSort(id);
            }
            else
                displayItems();
        }
        if (id == R.id.clear){
            TodoBase.viderListe(this);
            displayItems();
            Toast.makeText(MainActivity.this, "Vous avez vidé votre liste", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACT_ADD_ITEM:
                // Cursor is not dynamic, we have to fetch data again
                // Not optimal, but a dynamic cursor requires a CursorLoader,
                // which requires a ContentProvider... More work, more difficult
                displayItems();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putBoolean(SORTED, sorted);
        editor.commit();

    }

    public void createAlertDialogSort(final long ide){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.alerttri, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setPositiveButton("Ascendant", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                valtri=1;
                displayItems();
            }
        });
        alertDialogBuilder.setNegativeButton("Descendant", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                valtri=2;
                displayItems();
            }
        });


        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }


    public void createAlertDialog(final long ide){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.alertdialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
alertDialogBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int id) {
        // if this button is clicked, close
        // current activity
        deleteitem((long) ide);
    }

});
        alertDialogBuilder.setNegativeButton("NON ME SUPPRIME PAS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, close
                // current activity
            }

        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    public void createAlertDialogModif(final long ide){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.alertdialogmodif, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, close
                // current activity
                EditText et = (EditText) promptView.findViewById(R.id.textView3);
                String t = et.getText().toString();
                modifieritem((long) ide, t);
            }
        });
        alertDialogBuilder.setNegativeButton("PAS DE MODIF", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, close
                // current activity
            }

        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    public void deleteitem(long id){
        TodoBase.supprimerItem(this, id);
        Toast.makeText(MainActivity.this, "Suppression effectué", Toast.LENGTH_SHORT).show();
        displayItems();
    }

    public void modifieritem(long id,String val){
        TodoBase.modifierItem(this, id, val);
        Toast.makeText(MainActivity.this, "Modification effectué", Toast.LENGTH_SHORT).show();
        displayItems();
    }

    public void ajouteritem(View v){
        Intent addItem = new Intent(this, AddItemActivity.class);
        startActivityForResult(addItem, ACT_ADD_ITEM);
    }



}
