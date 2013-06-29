package im.grusis.mkb.core.bot;

import java.util.concurrent.Callable;

import im.grusis.mkb.core.exception.MkbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MkbBot<T> implements Callable<T> {

  private static final Logger LOG = LoggerFactory.getLogger(MkbBot.class);

  protected String username;

  protected MkbBot(String username) {
    this.username = username;
  }

  protected abstract T bot() throws MkbException;

  @Override
  public T call() throws Exception {
    return bot();
  }
}
