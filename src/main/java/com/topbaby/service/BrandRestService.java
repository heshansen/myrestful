package com.topbaby.service;

import com.topbaby.ecommerce.product.entity.Brand;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;

import java.util.List;
import java.util.Map;

/**
 * Created by heshansen on 16-11-30.
 */
public interface BrandRestService {
     Boolean existBrand(Long merchantSeq,Long brandSeq) throws Exception;
     List<Map<String, Object>> getBrand(Long merchantSeq) throws Exception;
     PageModel<Brand> getBrandPageList(Long merchantSeq, PageQueryModel pageQueryModel) throws Exception;
}
