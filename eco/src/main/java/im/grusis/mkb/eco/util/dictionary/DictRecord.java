package im.grusis.mkb.eco.util.dictionary;

import im.grusis.mkb.core.util.MkbDictionary;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午6:05
 */
public interface DictRecord<T extends MkbDictionary> {

 public void update(T dict);

 public void apply(T dict);
}
