package com.topbaby.entity;

import com.topbaby.ecommerce.brandshop.vo.BrandshopStocksProdutVO;
import com.topbaby.entity.base.BaseResponseMsg;
import org.esbuilder.business.model.IPageQueryModel;

import java.util.List;

/**
 * Created by xianghui on 16-12-28.
 * * details： 导购员-上架商品信息
 */

    public class BrandshopUserProductsInfo extends BaseResponseMsg {
        /** 商品信息*/
        private List<BrandshopStocksProdutVO> brandshopStocksProdutVOs ;
        /** 门店名称*/
        private String brandshopName ;
        private IPageQueryModel pageQueryModel ;
        private int totalRecord ;
        private String exMsg ;

        public BrandshopUserProductsInfo(){
            super();
        }

        public String getExMsg() {
            return exMsg;
        }

        public void setExMsg(String exMsg) {
            this.exMsg = exMsg;
        }

        public List<BrandshopStocksProdutVO> getBrandshopStocksProdutVOs() {
            return brandshopStocksProdutVOs;
        }

        public void setBrandshopStocksProdutVOs(List<BrandshopStocksProdutVO> brandshopStocksProdutVOs) {
            this.brandshopStocksProdutVOs = brandshopStocksProdutVOs;
        }

        public IPageQueryModel getPageQueryModel() {
            return pageQueryModel;
        }

        public void setPageQueryModel(IPageQueryModel pageQueryModel) {
            this.pageQueryModel = pageQueryModel;

        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public String getBrandshopName() {
            return brandshopName;
        }

        public void setBrandshopName(String brandshopName) {
            this.brandshopName = brandshopName;
        }
    }


