package com.puongra.neotest.Model;


import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import com.puongra.neotest.Entity.Sport;


import android.content.Context;
import android.util.Log;

public class SportDAO extends DB {

	public SportDAO(Context ctx){
		super(ctx);
	}

	public OID getObjectId(Sport spt){
		openConn();
		OID oid = null;
		IQuery query = new CriteriaQuery(Sport.class,Where.equal("name", spt.name));
		try{
			Objects<Sport> sports = this.odb.getObjects(query);
			if(sports.size() > 0)
				oid = this.odb.getObjectId(sports.getFirst());
		}catch(Exception e){
			Log.e(TAG,"Erro ao Recuperar OID");
		}finally{
			closeConn();
		}
		return oid;
	}
	public void update(Sport spt, OID oid) {
		openConn();
		try{
			Sport uSport = (Sport) this.odb.getObjectFromId(oid);
			uSport.name = spt.name;
			this.odb.store(uSport);

		}catch(Exception e){
			Log.e(TAG, "Erro ao Atualizar o Objeto");
		}finally{
			closeConn();
		}
	}

	public void delete(Sport obj){
		OID oid = getObjectId(obj);
		deleteFromId(oid);
	}
}
