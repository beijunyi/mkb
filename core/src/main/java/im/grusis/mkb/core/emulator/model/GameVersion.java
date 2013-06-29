package im.grusis.mkb.core.emulator.model;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 上午12:29
 */
public class GameVersion {
  private String platform;
  private String language;
  private String versionClient;
  private String versionBuild;
  private int mapMax;

  public GameVersion(String platform, String language, String versionClient, String versionBuild, int mapMax) {
    this.platform = platform;
    this.language = language;
    this.versionClient = versionClient;
    this.versionBuild = versionBuild;
    this.mapMax = mapMax;
  }

  public String getPlatform() {
    return platform;
  }

  public String getLanguage() {
    return language;
  }

  public String getVersionClient() {
    return versionClient;
  }

  public String getVersionBuild() {
    return versionBuild;
  }

  public int getMapMax() {
    return mapMax;
  }
}
