package com.puongra.neotest.Model;


import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import com.puongra.neotest.Entity.Player;

import android.content.Context;
import android.util.Log;

public class PlayerDAO extends DB {

	public PlayerDAO(Context ctx){
		super(ctx);
	}

	public OID getObjectId(Player obj){
		openConn();
		OID oid = null;
		IQuery query = new CriteriaQuery(Player.class,Where.equal("name", obj.name));
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
	public void update(Player nObj, OID oid) {
		openConn();
		try{
			Player uObj = (Player)this.odb.getObjectFromId(oid);
			uObj.name = nObj.name;
			this.odb.store(uObj);
		}catch(Exception e){
			Log.e(TAG, "Erro ao Atualizar o Objeto");
		}finally{
			closeConn();
		}
	}

	public void delete(Player obj){
		OID oid = getObjectId(obj);
		delete(oid);
	}
	
	private void delete(OID oid){
		openConn();
		try{
			Player play = (Player) odb.getObjectFromId(oid);
			odb.delete(play.favoriteSport);
			odb.delete(play);
		}catch(Exception e){
			Log.e(TAG,"Erro ao Excluir Player");
		}finally{
			closeConn();
		}
	}
}
