package concurrency2.vehicles.delegation;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class DelegatingVehicleTracker {

  ConcurrentMap<String, Point> locations;
  Map<String, Point> unmodifiableMap;

  DelegatingVehicleTracker(Map<String, Point> points) {
    locations = new ConcurrentHashMap<String, Point>(points);
    unmodifiableMap = Collections.unmodifiableMap(locations);
  }

  Map<String, Point> getLocations() {
    return unmodifiableMap;
  }

  Point getLocation(String id) {
    return locations.get(id);
  }

  void setLocation(String id, int x, int y) {
    if (locations.replace(id, new Point(x, y)) == null)
      throw new IllegalArgumentException(
          "invalid vehicle name: " + id);
  }
  
  
  
//  private static void render(DelegatingVehicleTracker vehicles) {
//    Map<String, Point> locations = vehicles.getLocations();
//    for (String key : locations.keySet())
//        renderVehicle(key, locations.get(key));
//  }
//
//  private static void renderVehicle(String key, Point point) {
//    // TODO Auto-generated method stub
//  }
//  
//  void vehicleMoved(VehicleMovedEvent evt) {
//    DelegatingVehicleTracker vehicles = new DelegatingVehicleTracker(null);
//    
//Point loc = evt.getNewLocation();
//vehicles.setLocation(evt.getVehicleId(), loc.x, loc.y);
//}
//  
//  public class VehicleMovedEvent {
//
//    public concurrency2.vehicles.delegation.Point getNewLocation() {
//      // TODO Auto-generated method stub
//      return null;
//    }
//
//    public String getVehicleId() {
//      // TODO Auto-generated method stub
//      return null;
//    }
//
//  }
//


}
