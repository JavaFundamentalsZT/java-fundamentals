package threadsafety.vehicles.monitor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class MonitorVehicleTracker {
  Map<String, MutablePoint> locations;
  MonitorVehicleTracker(Map<String, MutablePoint> locations) {
    this.locations = deepCopy(locations);
  }
  synchronized Map<String, MutablePoint> getLocations() {
    return deepCopy(locations);
  }
  synchronized MutablePoint getLocation(String id) {
    MutablePoint loc = locations.get(id);
    return loc == null ? null : new MutablePoint(loc);
  }
  synchronized void setLocation(String id, int x, int y) {
    MutablePoint loc = locations.get(id);
    if (loc == null)
      throw new IllegalArgumentException("No such ID: " + id);
    loc.x = x;
    loc.y = y;
  }
  static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
    Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();
    for (String id : m.keySet())
      result.put(id, new MutablePoint(m.get(id)));
    return Collections.unmodifiableMap(result);
  }
}
