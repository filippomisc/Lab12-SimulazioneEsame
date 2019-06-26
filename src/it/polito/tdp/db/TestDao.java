package it.polito.tdp.db;

import it.polito.tdp.model.EventIdMap;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		EventsDaoMap daoMap = new EventsDaoMap();
		EventIdMap idMap = new EventIdMap();
		
			System.out.println(dao.listAllEvents(2016).size());
//			System.out.println(dao.listAnni().size());
//			System.out.println(dao.allVerticesAnno(2016));
//			System.out.println(dao.listaCriminiQuartieri(2016));
			System.out.println(daoMap.listAllEventsAnno(2016, idMap).size());


	}

}
