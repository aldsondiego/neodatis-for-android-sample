package com.puongra.neotest.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;

import com.puongra.neotest.MainActivity;


import android.content.Context;
import android.util.Log;

/**
 * 
 * @author Aldson Diego
 * @version 2013.1001
 * @since 1.0
 *
 */
public class GenericDAO {

	/**
	 * Arquivo para definir o diretorio do SDCard
	 */
	private File sd = null;
	/**
	 * Nome do banco de dados que será usado
	 */
	private String ODB_NAME = "testedb.neodatis";
	
	/**
	 * Propriedade para Instânciar o objeto principal do banco de dados Neodatis
	 */
	private ODB odb= null;
	
	/**
	 * Arquivo do Banco de dados
	 */
	private File file= null;
	

	public GenericDAO(Context ctx){
		sd = ctx.getExternalFilesDir(null);
		file = new File(this.sd, this.ODB_NAME);
	}

	public ODB Open(){
		odb = ODBFactory.open(file.getAbsolutePath());
		return odb;
	}
	
	public void Close(){
		if(odb != null){
			odb.close();
		}
	}
	/**
	 * Método para salvar um objeto no banco de dados Neodatis
	 * 
	 * @param obj Objeto genérico, pois aceita qualquer objeto
	 * @return Chave OID do objeto gravado
	 */
	public OID Save(Object obj){

		OID oid = null;
		try{
			odb = ODBFactory.open(file.getAbsolutePath());
			oid = odb.store(obj);
		}finally{
			if(odb != null)
				odb.close();
		}
		return oid;
	}
	
	/**
	 * Método para salvar uma lista de objetos no banco de dados Neodatis
	 * 
	 * @param objs lista de objetos
	 */
	public void Save(ArrayList objs){
		try{
			odb = ODBFactory.open(file.getAbsolutePath());
			Iterator<?> iter = objs.iterator();
			while(iter.hasNext()){
				odb.store(iter.next());
			}	
		}finally{
			if(odb != null)
				odb.close();
		}
	}

	/**
	 * 
	 * @param type define o tipo de objeto a ser resgatado do banco de dados
	 * @return Lista de objetos definidos pelo "type"
	 */
	public Objects<?> List(Class<?> type){

		Objects<?> objs = null;
		try{
			odb = ODBFactory.open(file.getAbsolutePath());
			objs = odb.getObjects(type);
		}catch(Exception e){
			objs = null;
		}finally{
			if(odb != null)
				odb.close();
		}
		return objs;
	}

	/**
	 * Método para excluir o objeto
	 * 
	 * @param obj
	 */
	public void Delete(Object obj){
		try{
			odb = ODBFactory.open(file.getAbsolutePath());
				odb.delete(obj);
		}finally{
			if(odb != null)
				odb.close();
		}
	}
	
	public void Delete(Objects<?> objs){
		try{
			odb = ODBFactory.open(file.getAbsolutePath());
			Iterator<?> iter = objs.iterator();
			while(iter.hasNext()){
				odb.delete(iter.next());
			}
		}finally{
			if(odb != null)
				odb.close();
		}
	}

	/**
	 * 
	 * @param type
	 */
	public void Clear(Class<?> type){
		try{
			odb = ODBFactory.open(file.getAbsolutePath());
			Objects<?> objs = odb.getObjects(type);
			if(objs.size() > 0){
				Iterator<?> iter = objs.iterator();
				while(iter.hasNext()){
					odb.delete(iter.next());
				}
			}
		}finally{
			if(odb != null)
				odb.close();
		}
	}
	
	public void LogList(Objects<?> list, Object obj){
		Iterator<?> iter = list.iterator();
		while(iter.hasNext()){
			obj = iter.next();
			Log.i(MainActivity.class.getName(),obj.toString());
		}
	}

}
