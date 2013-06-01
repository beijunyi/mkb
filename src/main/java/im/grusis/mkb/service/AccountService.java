package im.grusis.mkb.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.internal.StringMapSetArchive;
import im.grusis.mkb.internal.StringSetArchive;
import im.grusis.mkb.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 上午12:01
 */
@Service
public class AccountService {
  AccountRepository accountRepository;

  private Map<String, MkbAccount> macLookup = new LinkedHashMap<String, MkbAccount>();
  private Map<String, MkbAccount> usernameLookup = new LinkedHashMap<String, MkbAccount>();

  private StringSetArchive usedUsername;
  private StringMapSetArchive usedNickname;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
    List<MkbAccount> accounts = accountRepository.readAll();
    for(MkbAccount account : accounts) {
      updateLookup(account);
    }

  }

  private void updateLookup(MkbAccount account) {
    String mac = account.getMac();
    if(mac != null) {
      macLookup.put(mac, account);
    }
    String username = account.getUsername();
    if(username != null) {
      usernameLookup.put(username, account);
    }
  }

  private void deleteAccount(String username) {
    MkbAccount account = usernameLookup.get(username);
    macLookup.remove(account.getMac());
    usernameLookup.remove(account.getUsername());
    accountRepository.deleteAccount(username);
  }

  public MkbAccount findAccountByMac(String mac) {
    return macLookup.get(mac);
  }

  public MkbAccount findAccountByUsername(String username) {
    return usernameLookup.get(username);
  }

  public void saveAccount(MkbAccount account) {
    updateLookup(account);
    accountRepository.createOrUpdateAccount(account);
  }
}
