package im.grusis.mkb.core.repository.model;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:06
 */
public class MkbAssets<T> {

  private String assetName;
  private T asset;

  public MkbAssets() {
  }

  public MkbAssets(String assetName) {
    this.assetName = assetName;
  }

  public String getAssetName() {
    return assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public T getAsset() {
    return asset;
  }

  public void setAsset(T asset) {
    this.asset = asset;
  }
}
