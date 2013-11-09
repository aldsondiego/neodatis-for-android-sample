package com.puongra.neotest.Entity;

import org.neodatis.odb.OID;

public class MyObject extends Object {
	protected OID oid;
	
	public void setOid(OID oid){
		this.oid = oid;
	}
	
	public OID getOid(){
		return this.oid;
	}
}
