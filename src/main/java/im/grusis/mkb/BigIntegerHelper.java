package im.grusis.mkb;

/**
 * User: Mothership
 * Date: 13-5-17
 * Time: 下午7:52
 */
public class BigIntegerHelper {

  public int[] rand(int n, boolean s) {
    int[] lowBitMasks = new int[]{0x0000, 0x0001, 0x0003, 0x0007, 0x000f, 0x001f, 0x003f, 0x007f, 0x00ff, 0x01ff, 0x03ff, 0x07ff, 0x0fff, 0x1fff, 0x3fff, 0x7fff};
    int r = n % 16;
    int q = n >> 4;
    int[] result = new int[q];
    for(int i = 0; i < q; i++) {
      result[i] = (int)Math.floor(Math.random() * 0xffff);
    }
    if(r != 0) {
      result[q] = (int)Math.floor(Math.random() * lowBitMasks[r]);
      if(s) {
        result[q] |= 1 << (r - 1);
      }
    } else if(s) {
      result[q - 1] |= 0x8000;
    }
    return result;
  }
}
