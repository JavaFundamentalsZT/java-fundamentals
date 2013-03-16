package threadsafety.vehicles.monitor;

class MutablePoint {
  int x, y;
  MutablePoint(MutablePoint p) {
    this.x = p.x;
    this.y = p.y;
  }
}
