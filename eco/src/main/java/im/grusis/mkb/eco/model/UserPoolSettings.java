package im.grusis.mkb.eco.model;

import java.util.*;

public abstract class UserPoolSettings<T> extends EcoSettings {

  private TreeSet<String> usernames;
  private T defaultSettings;
  private Map<String, T> customSettings;

  public UserPoolSettings(String name, T defaultSettings) {
    super(name);
    this.defaultSettings = defaultSettings;
  }

  public void addUsers(List<String> users) {
    if(usernames == null) {
      usernames = new TreeSet<String>();
    }
    usernames.addAll(users);
  }

  public void removeUsers(List<String> users) {
    if(usernames != null) {
      usernames.removeAll(users);
    }
  }

  public TreeSet<String> getUsernames() {
    return usernames;
  }

  public T getDefaultSettings() {
    return defaultSettings;
  }

  public void setDefaultSettings(T defaultSettings) {
    this.defaultSettings = defaultSettings;
  }

  public Map<String, T> getCustomSettings() {
    return customSettings;
  }

  public T getSettings(String username) {
    if(customSettings == null) {
      return null;
    }
    T settings = customSettings.get(username);
    if(settings == null) {
      return defaultSettings;
    }
    return settings;
  }

  public void setSetting(String username, T settings) {
    if(customSettings == null) {
      customSettings = new HashMap<String, T>();
    }
    customSettings.put(username, settings);
  }
}
