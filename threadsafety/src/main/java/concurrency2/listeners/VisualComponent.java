package concurrency2.listeners;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class VisualComponent {
  List<KeyListener> keyListeners = new CopyOnWriteArrayList<KeyListener>();
  List<MouseListener> mouseListeners = new CopyOnWriteArrayList<MouseListener>();
  void addKeyListener(KeyListener listener) {
    keyListeners.add(listener);
  }
  void addMouseListener(MouseListener listener) {
    mouseListeners.add(listener);
  }
  void removeKeyListener(KeyListener listener) {
    keyListeners.remove(listener);
  }
  void removeMouseListener(MouseListener listener) {
    mouseListeners.remove(listener);
  }
}
