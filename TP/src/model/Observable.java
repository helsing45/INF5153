package model;

public interface Observable {
  void addObserver(Observer obs);
  void removeObserver();
  void notifyObserver(String str);
}