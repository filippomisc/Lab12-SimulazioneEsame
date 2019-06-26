package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.model.Event;


public class EventsDao {
	
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
	 * @param anno 
	 * @return lista di tipo Event (eventi)
	 */
	public List<Event> listAllEvents(int anno){
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
					list.add(new Event((int)res.getLong("incident_id"),//TODO se serve cambiare il formato da int a Long
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
							res.getInt("is_traffic")));
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

	
	/**METODO CHE RESTITUISCE UNA LISTA DI DISTRETTI (coici 
	 * codici identificativi dei distretti)
	 * 
	 * 
	 * QUERY: 
	 * SELECT DISTINCT district_id
	 * FROM events
	 * ORDER BY district_id
	 * 
	 * 
	 * @param anno
	 * @return Lista di interi (distretti)
	 */
	public List<Integer> allVerticesAnno(int anno) {
		String sql = "select distinct district_id " + 
				"from events " + 
				"where year(reported_date)=? " +
				"order by district_id" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("district_id"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	
	/**Metodo che CALCOLA MEDIA di tutti gli eventi di un distretto
	 * in un anno specifico
	 * 
	 * 
	 * QUERY:
	 * SELECT AVG(events.geo_lat) AS lat, AVG(events.geo_lon) AS lon
	 * FROM events
	 * WHERE events.district_id=?
	 * AND year(reported_date)=?
	 * 
	 * 
	 * @param vertice Integer
	 * @param anno int
	 * @return
	 */
	public LatLng calcolaAVGLatLong(Integer vertice, int anno) {
		String sql = "select avg(events.geo_lat) as lat, avg(events.geo_lon) as lon " + 
				"from events " + 
				"where events.district_id=? " + 
				"and year(reported_date)=?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, vertice);
			st.setInt(2, anno);
			
			LatLng coordinate=null;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					coordinate =new LatLng(res.getDouble("lat"),
							res.getDouble("lon"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return  coordinate;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	/**Metodo che serve a popolare il menu a tendina
	 * riporta una lista di tutti gli anni in cui sono 
	 * avvenuti gli eventi (una volta sola)
	 * 
	 * QUERY:
	 * SELECT DISTINCT year(reported_date) as anno
	 * FROM events
	 * ORDER BY year(reported_date)"
	 * 
	 * @return lista Anni (Integer)
	 */
	public List<Integer> listAnni(){
		String sql = "select distinct year(reported_date) as anno " + 
				"from events " + 
				"order by year(reported_date)" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					list.add(res.getInt("anno"));
					
			
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	/**METODO che restituisce una lista di quartieri ordinata in ordine 
	 * decrescente per numerro di eventi criminosi in un certo anno
	 * 
	 * 
	 * QUERY:
	 * SELECT COUNT(e.incident_id), e.neighborhood_id
	 * FROM events e
	 * WHERE year(e.reported_date)=?
	 * 						////AND e.neighborhood_id="?"/////
	 * GROUP BY e.neighborhood_id
	 * ORDER BY COUNT(e.incident_id) DESC
	 * 
	 * 
	 * @param anno
	 * @return LISTA DI QUARTIERI CON IN NUMERO DI CRIMINI DECRESCENTI //
	 */
	public List<ClasseQuartiere> listaCriminiQuartieri(int anno){
		String sql = "SELECT COUNT(e.incident_id) as cnt, e.neighborhood_id as quartiere " + 
				"FROM events e\n" + 
				"WHERE year(e.reported_date)=2014 " + 
				"/*AND e.neighborhood_id=\"city-park\"*/ " + 
				"GROUP BY e.neighborhood_id " + 
				"ORDER BY COUNT(e.incident_id) DESC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			List<ClasseQuartiere> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					list.add(new ClasseQuartiere( res.getInt("cnt"), res.getString("quartiere")));
					
			
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
}
