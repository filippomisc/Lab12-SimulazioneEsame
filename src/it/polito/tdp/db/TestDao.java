package it.polito.tdp.db;


public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		
			System.out.println(dao.listAllEvents(2016).size());
			System.out.println(dao.listAnni().size());

	}

}
