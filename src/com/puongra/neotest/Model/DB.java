package com.puongra.neotest.Model;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;

import android.content.Context;
import android.util.Log;

import com.puongra.neotest.Entity.*;
/**
 * 
 * @author Aldson Diego
 * @version 2013.1001
 * @since 1.0
 *
 */
abstract class DB {

	protected final String TAG = "com.puongra.neotest";
	protected File sd = null;
	protected final String ODB_NAME = "testedb.neodatis";
	protected ODB odb= null;
	protected File file= null;

	protected String path = null;

	public DB(Context ctx){
		sd = ctx.getExternalFilesDir(null);
		//file = new File(this.sd, this.ODB_NAME);
		path = sd.getAbsolutePath()+"/"+this.ODB_NAME;
	}

	public void openConn(){
		try{
			odb = ODBFactory.open(path);
		}catch(Exception e){
			Log.e(TAG,"Não foi possivel instanciar o ODB");
		}
	}

	public void closeConn(){
		if(this.odb != null){
			this.odb.close();
		}
	}

	public OID create(Object obj){
		openConn();
		OID oid = null;
		try{
			oid = this.odb.store(obj);
		}catch(Exception e){
			Log.e(TAG, "Erro na gravação do Objeto");
		}finally{
			closeConn();
		}
		return oid;
	}

	public void deleteFromId(OID oid){
		openConn();
		try{
			this.odb.delete(this.odb.getObjectFromId(oid));
		}catch(Exception e){
			Log.e(TAG, "Erro ao Deletar Objeto");
		}finally{
			closeConn();
		}
	}//*/

	public Objects<?> getAll(Class<?> type){
		openConn();
		Objects<?> objs = null;
		try{
			objs = odb.getObjects(type);
		}catch(Exception e){
			Log.e(TAG, "Erro ao Recuperar Objetos");
		}finally{
			closeConn();
		}
		return objs;
	}

	/**
	 * Method <b>clear()</b><br/>
	 * Cuidado!, este método apaga todos objetos da classe especificada;
	 * 
	 * @param type - Tipo de Classe;
	 */
	public void clear(Class<?> type){
		openConn();
		try{
			Log.w(TAG, "Cleaning this class type in DB");
			Objects<?> objs = odb.getObjects(type);
			while(objs.hasNext()){
				odb.delete(objs.next());
			}
		}catch(Exception e){
			Log.e(TAG, "Erro ao deletar Objetos");
		}finally{
			closeConn();
		}
	}

	public void loggerOf(Class<?> type){
		Objects<?> objs = getAll(type);
		while(objs.hasNext()){
			Log.i(TAG,objs.next().toString());
		}
	}
}