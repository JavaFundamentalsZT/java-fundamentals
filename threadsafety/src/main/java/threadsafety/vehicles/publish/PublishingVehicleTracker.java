package threadsafety.vehicles.publish;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class PublishingVehicleTracker {
  final Map<String, SafePoint> locations;
  final Map<String, SafePoint> unmodifiableMap;
  PublishingVehicleTracker(
      Map<String, SafePoint> locations) {
    this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
    this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
  }
  Map<String, SafePoint> getLocations() {
    return unmodifiableMap;
  }
  SafePoint getLocation(String id) {
    return locations.get(id);
  }
  void setLocation(String id, int x, int y) {
    if (!locations.containsKey(id))
      throw new IllegalArgumentException("invalid vehicle name: " + id);
    locations.get(id).set(x, y);
  }
}
