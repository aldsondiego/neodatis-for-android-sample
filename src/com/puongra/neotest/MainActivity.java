package com.puongra.neotest;

import java.util.Date;

import org.neodatis.odb.OID;

import com.puongra.neotest.Entity.Game;
import com.puongra.neotest.Entity.Player;
import com.puongra.neotest.Entity.Sport;
import com.puongra.neotest.Entity.Team;
import com.puongra.neotest.Model.GameDAO;
import com.puongra.neotest.Model.PlayerDAO;
import com.puongra.neotest.Model.SportDAO;
import com.puongra.neotest.Model.TeamDAO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private final String TAG = "com.puongra.task";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Context context = getBaseContext();
		
		SportDAO sDao = new SportDAO(context);
		PlayerDAO pDao = new PlayerDAO(context);
		TeamDAO tDao = new TeamDAO(context);
		GameDAO gDao = new GameDAO(context);
		
		//Erase Objects in Database
		sDao.clear(Sport.class);
		pDao.clear(Player.class);
		tDao.clear(Team.class);
		gDao.clear(Game.class);
		
		//create some sports
		Sport s1 = new Sport("Basketball");
		Sport s2 = new Sport("Futball");
		Sport s3 = new Sport("Handball");
		sDao.create(s1);
		sDao.create(s2);
		sDao.create(s3);
		
		//sport
		Sport volleyball = new Sport("Volleyball");
		
		//players
		Player p1 = new Player("Joel Pereira",new Date(), volleyball);
		Player p2 = new Player("Ricardo Maciel",new Date(), volleyball);
		Player p3 = new Player("Paulo Barbosa",new Date(), volleyball);
		Player p4 = new Player("Emanuel da Silva",new Date(), volleyball);
		
		//Teams
		Team team1 = new Team("Jaru");
		Team team2 = new Team("Ouro Preto D'Oeste");
		
		//add two players in each Team
		team1.addPlayer(p1);
		team1.addPlayer(p2);
		
		team2.addPlayer(p3);
		team2.addPlayer(p4);
		
		//The Game
		Game game = new Game(new Date(),volleyball,team1,team2);

		//create
		gDao.create(game);
		sDao.loggerOf(Sport.class);
		
		//update
		OID oid = gDao.getObjectId(game);//Date 'when' is the key to get object OID;
		game.setSport(s3);
		gDao.novoUpdate(game, oid);
		
		//delete
		//gDao.delete(game);
		
		//loggers
		pDao.loggerOf(Player.class);
		tDao.loggerOf(Team.class);
		gDao.loggerOf(Game.class);
		sDao.loggerOf(Sport.class);
		
		//*/
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
