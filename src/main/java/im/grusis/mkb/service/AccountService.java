package im.grusis.mkb.service;

import java.util.*;

import im.grusis.mkb.emulator.emulator.core.model.basic.UserInfo;
import im.grusis.mkb.internal.MkAccount;
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

  private Map<String, MkAccount> macLookup = new LinkedHashMap<String, MkAccount>();
  private Map<String, MkAccount> usernameLookup = new LinkedHashMap<String, MkAccount>();
  private Map<String, MkAccount> nicknameLookup = new LinkedHashMap<String, MkAccount>();

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
    List<MkAccount> accounts = accountRepository.readAll();
    for(MkAccount account : accounts) {
      updateLookup(account);
    }
  }

  private void updateLookup(MkAccount account) {
    String mac = account.getMac();
    if(mac != null) {
      macLookup.put(mac, account);
    }
    String username = account.getUsername();
    if(username != null) {
      usernameLookup.put(username, account);
    }
    UserInfo userInfo;
    String nickname;
    if((userInfo = account.getUserInfo()) != null) {
      if((nickname = userInfo.getNickName()) != null) {
        nicknameLookup.put(nickname, account);
      }
    }
  }

  private void releaseMac(String mac) {
    MkAccount account = macLookup.remove(mac);
    account.setMac(null);
    saveAccount(account);
  }

  public MkAccount findAccountByMac(String mac) {
    return macLookup.get(mac);
  }

  public MkAccount findAccountByUsername(String username) {
    return usernameLookup.get(username);
  }

  public MkAccount findAccountByNickname(String nickname) {
    return nicknameLookup.get(nickname);
  }

  public void saveAccount(MkAccount account) {
    updateLookup(account);
    accountRepository.createOrUpdateAccount(account);
  }
}
