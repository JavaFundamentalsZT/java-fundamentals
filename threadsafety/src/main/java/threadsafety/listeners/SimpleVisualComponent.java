package threadsafety.listeners;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

class SimpleVisualComponent {
  List<KeyListener> keyListeners = new ArrayList<KeyListener>();
  List<MouseListener> mouseListeners = new ArrayList<MouseListener>();
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
