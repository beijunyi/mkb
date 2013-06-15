package im.grusis.mkb.emulator.password;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午10:02
 */
public class HashPasswordGenerator implements PasswordGenerator {

  @Override
  public String generate(String username) {
    return "MkbHash" + Math.abs(username.hashCode()) % 1000;
  }
}
