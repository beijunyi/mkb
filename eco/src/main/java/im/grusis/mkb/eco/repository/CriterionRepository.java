package im.grusis.mkb.eco.repository;

import im.grusis.mkb.core.repository.MkbRepository;
import im.grusis.mkb.eco.model.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class CriterionRepository extends MkbRepository<Criterion> {

  private static final Logger LOG = LoggerFactory.getLogger(CriterionRepository.class);

  public CriterionRepository() {
    super("criterion", Criterion.class);
  }

  public<T extends Criterion> T getCriterion(String index, Class<T> clazz) {
    return read(index, clazz);
  }

  public void createOrUpdateCriterion(Criterion criterion) {
    String index = criterion.getName();
    if(index == null || index.isEmpty()) {
      LOG.error("Cannot create or update criterion with no name.");
      return;
    }
    write(index, criterion, true);
  }

}
