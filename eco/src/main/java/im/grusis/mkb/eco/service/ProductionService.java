package im.grusis.mkb.eco.service;

import java.util.Map;
import javax.annotation.PostConstruct;

import im.grusis.mkb.eco.model.ProductionCriteria;
import im.grusis.mkb.eco.model.ProductionCriterion;
import im.grusis.mkb.eco.repository.CriterionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-6-27
 * Time: 上午12:30
 */
@Service
public class ProductionService {

  @Autowired CriterionRepository criterionRepository;

  private ProductionCriterion productionCriterion;

  @PostConstruct
  public void init() {
    productionCriterion = criterionRepository.getCriterion(ProductionCriterion.PRODUCTION, ProductionCriterion.class);
  }

  public ProductionCriterion getProductionCriterion() {
    return productionCriterion;
  }

  public ProductionCriterion addProductionCriteria(Map<Integer, Integer> cardCount) {
    if(productionCriterion == null) {
      productionCriterion = new ProductionCriterion();
    }
    productionCriterion.addCriteria(new ProductionCriteria(cardCount));
    criterionRepository.createOrUpdateCriterion(productionCriterion);
    return productionCriterion;
  }

  public ProductionCriterion removeProductionCriteria(int id) {
    if(productionCriterion == null) {
      productionCriterion = new ProductionCriterion();
    }
    productionCriterion.removeCriteria(id);
    criterionRepository.createOrUpdateCriterion(productionCriterion);
    return productionCriterion;
  }

  public ProductionCriteria editProductionCriteriaComment(int id, String comment) {
    if(productionCriterion == null) {
      return null;
    }
    ProductionCriteria productionCriteria = productionCriterion.getCriteria(id);
    if(productionCriteria == null) {
      return null;
    }
    productionCriteria.setComment(comment);
    criterionRepository.createOrUpdateCriterion(productionCriterion);
    return productionCriteria;
  }
}
