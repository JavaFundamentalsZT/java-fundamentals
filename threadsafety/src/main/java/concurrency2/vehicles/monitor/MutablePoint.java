package concurrency2.vehicles.monitor;

class MutablePoint {
  int x, y;
  MutablePoint(MutablePoint p) {
    this.x = p.x;
    this.y = p.y;
  }
}
