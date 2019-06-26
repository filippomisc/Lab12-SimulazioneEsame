package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.model.Event;
import it.polito.tdp.model.EventIdMap;

public class EventsDaoMap {

	
	/**METODO che restituisce 
	 * gli eventi di un certo anno
	 * 
	 * utile da usare per qualsiasi tipo 
	 * di classe si voglia creare dal database 
	 * 
	 * QUERY:
	 * SELECT *
	 * FROM events
	 * WHERE year(reported_date)=?
	 * 
	 * @param anno, idMap
	 * @return lista di tipo Event (eventi)
	 */
	public List<Event> listAllEventsAnno(int anno, EventIdMap idMap){
		String sql = "SELECT * " + 
				"FROM events " + 
				"where year(reported_date)=?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Event event = new Event((int)res.getLong("incident_id"),//TODO se serve cambiare il formato da int a Long
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic"));
					
					
					list.add(idMap.get(event));
					
					
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	
	
	/**METODO che restituisce tutti
	 * gli eventi
	 * 
	 * utile da usare per qualsiasi tipo 
	 * di classe si voglia creare dal database 
	 * 
	 * QUERY:
	 * SELECT *
	 * FROM events
	 * 
	 * 
	 * @param idMap
	 * @return lista di tipo Event (eventi)
	 */
	public List<Event> listAllEvents(EventIdMap idMap){
		String sql = "SELECT * " + 
				"FROM events";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Event event = new Event((int)res.getLong("incident_id"),//TODO se serve cambiare il formato da int a Long
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic"));
					
					
					list.add(idMap.get(event));
					
					
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

}
