package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.EvolutionResult;
import im.grusis.mkb.core.emulator.game.model.response.EvolutionPreviewResponse;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-28
 * Time: 下午11:28
 */
@Component
public class EmulatorEvolution {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorEvolution.class);

  @Autowired EmulatorCore core;

  public EvolutionResult preview(String username, int type, long target, long source) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("Type", Integer.toString(type));
    params.put("UserCardId1", Long.toString(target));
    params.put("UserCardId2", Long.toString(source));
    EvolutionPreviewResponse response = core.gameDoAction(username, "evolution.php", "Preview", params, EvolutionPreviewResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public EvolutionResult evolve(String username, long target, long source) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("UserCardId1", Long.toString(target));
    params.put("UserCardId2", Long.toString(source));
    EvolutionPreviewResponse response = core.gameDoAction(username, "evolution.php", null, params, EvolutionPreviewResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }
}
