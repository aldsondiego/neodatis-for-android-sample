package com.puongra.neotest.Model;

import java.util.Iterator;

import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import android.content.Context;
import android.util.Log;

import com.puongra.neotest.Entity.Game;

public class GameDAO extends DB{

	public GameDAO(Context ctx){
		super(ctx);
	}
	
	public OID getObjectId(Game obj){
		openConn();
		OID oid = null;
		IQuery query = new CriteriaQuery(Game.class,Where.equal("when", obj.getWhen()));
		try{
			Objects<?> objs = this.odb.getObjects(query);
			if(objs.size() > 0)
				oid = this.odb.getObjectId(objs.getFirst());
		}catch(Exception e){
			Log.e(TAG,"Erro ao Recuperar OID");
		}finally{
			closeConn();
		}
		return oid;
	}
	public void update(Game nObj, OID oid) {
		openConn();
		try{
			Game uObj = (Game)this.odb.getObjectFromId(oid);
			uObj.setWhen(nObj.getWhen());
			uObj.getSport().name = nObj.getSport().name;
			Iterator iter = uObj.getTeam1().getPlayers().iterator();
			while(iter.hasNext()){
				odb.delete(iter.next());
			}
			uObj.getTeam1().setPlayers(nObj.getTeam1().getPlayers());
			iter = uObj.getTeam2().getPlayers().iterator();
			while(iter.hasNext()){
				odb.delete(iter.next());
			}
			uObj.getTeam2().setPlayers(nObj.getTeam2().getPlayers());
			uObj.getTeam1().setName(nObj.getTeam1().getName());
			uObj.getTeam2().setName(nObj.getTeam2().getName());
			uObj.setResult(nObj.getResult());
			this.odb.store(uObj);
		}catch(Exception e){
			Log.e(TAG, "Erro ao Atualizar o Objeto");
		}finally{
			closeConn();
		}
	}

	public void delete(Game obj){
		OID oid = getObjectId(obj);
		delete(oid);
	}
	
	private void delete(OID oid){
		openConn();
		try{
			Game obj = (Game) odb.getObjectFromId(oid);
			odb.delete(obj.getSport());
			Iterator iter = obj.getTeam1().getPlayers().iterator();
			while(iter.hasNext()){
			odb.delete(iter.next());
			}
			iter = obj.getTeam2().getPlayers().iterator();
			while(iter.hasNext()){
				odb.delete(iter.next());
			}
			odb.delete(obj.getTeam1());
			odb.delete(obj.getTeam2());
			odb.delete(obj);
		}catch(Exception e){
			Log.e(TAG,"Erro ao Excluir Player");
		}finally{
			closeConn();
		}
	}
}
