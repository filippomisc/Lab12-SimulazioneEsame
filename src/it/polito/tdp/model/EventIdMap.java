package it.polito.tdp.model;

import java.util.*;
public class EventIdMap {

		
		private Map<Integer, Event> map;//TODO teoricamente era Long
		
		public EventIdMap() {
			map = new HashMap<>();
		}
		
		public Event get(Integer id) {
			return map.get(id);
		}
		
		public Event get(Event oggetto) {
			Event old = map.get(oggetto.getIncident_id());
			if (old == null) {
				map.put(oggetto.getIncident_id(), oggetto);
				return oggetto;
			}
			return old;
		}
		
		public void put(Event oggetto, Integer id) {
			map.put(id, oggetto);
		}

		public Map<Integer, Event> getMap() {
			return map;
		}	
	
		

}
