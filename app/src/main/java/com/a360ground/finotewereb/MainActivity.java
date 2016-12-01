package com.a360ground.finotewereb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ፍኖት ወረብ");
        getSupportActionBar().setShowHideAnimationEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.beal);
        BealRecyclerAdapter bealRecyclerAdapter = new BealRecyclerAdapter(getBeal(),this);
        recyclerView.setAdapter(bealRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public List<Wereb> getWerebs(){
        List<Wereb> werebList = new ArrayList<>();
        String[] werebs = {"አርአዮ","ሚካኤል","መልአከ መኑ","በቃና","ዘገሊላ","ከብካብ"};
        String[] geez = {getString(R.string.westeAflage),getString(R.string.kedamiha),getString(R.string.westeAflage),getString(R.string.kedamiha),getString(R.string.westeAflage),getString(R.string.kedamiha)};
        String[] tirgum = {getString(R.string.westeAflage_tirgum),getString(R.string.westeAflage_tirgum),getString(R.string.westeAflage_tirgum),getString(R.string.westeAflage_tirgum),getString(R.string.westeAflage_tirgum),getString(R.string.westeAflage_tirgum)};
        for(int i=0;i<werebs.length;i++){
            Wereb wereb = new Wereb(werebs[i]);
            wereb.setWerebGeez(geez[i]);
            wereb.setWerebAmharic(tirgum[i]);
            werebList.add(wereb);
        }
        return werebList;
    }
    public List<Beal> getBeal(){
        return Arrays.asList(beal5(),beal1(),beal2(),beal3(),beal4(),beal6(),beal7(),beal8());
    }
    public Beal beal5(){
        Beal beal = new Beal("ጥቅምት አቡነ አረጋዊ",getWerebs());
        return beal;
    }
    public Beal beal1(){
        Beal beal = new Beal("ኅዳር ሚካኤል",getWerebs());
        return beal;
    }
    public Beal beal2(){
        Beal beal = new Beal("ኅዳር ጽዮን",getWerebs());
        return beal;
    }
    public Beal beal3(){
        Beal beal = new Beal("ጥምቀት",getWerebs());
        return beal;
    }
    public Beal beal4(){
        Beal beal = new Beal("የካቲት ኪዳነምህረት",getWerebs());
        return beal;
    }
    public Beal beal6(){
        Beal beal = new Beal("ጰራቅሊጦስ",getWerebs());
        return beal;
    }
    public Beal beal7(){
        Beal beal = new Beal("ሰኔ ሚካኤል",getWerebs());
        return beal;
    }
    public Beal beal8(){
        Beal beal = new Beal("ነሐሴ ኪዳነምህረት",getWerebs());
        return beal;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

}
