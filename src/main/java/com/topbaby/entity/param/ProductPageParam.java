package com.topbaby.entity.param;

/**
 * details： 商品分页数据筛选条件实体
 * Created by sxy on 16-12-1.
 */
public class ProductPageParam {
    /**
     * 状态
     */
    private String status;
    /**
     * 商品唯一编号(支持模糊匹配)
     */
    private String productNoVague;
    /**
     * 商品唯一货号(支持模糊匹配)
     */
    private String stockNoVague;
    /**
     * 商品名称(支持模糊匹配)
     */
    private String nameVague;
    /**
     * 商品品牌ID
     */
    private String brandSeq;
    /**
     * 商品种类ID
     */
    private Long catSeq;
    /**
     * 商户ID
     */
    private String merSeq;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductNoVague() {
        return productNoVague;
    }

    public void setProductNoVague(String productNoVague) {
        this.productNoVague = productNoVague;
    }

    public String getStockNoVague() {
        return stockNoVague;
    }

    public void setStockNoVague(String stockNoVague) {
        this.stockNoVague = stockNoVague;
    }

    public String getNameVague() {
        return nameVague;
    }

    public void setNameVague(String nameVague) {
        this.nameVague = nameVague;
    }

    public String getBrandSeq() {
        return brandSeq;
    }

    public void setBrandSeq(String brandSeq) {
        this.brandSeq = brandSeq;
    }

    public Long getCatSeq() {
        return catSeq;
    }

    public void setCatSeq(Long catSeq) {
        this.catSeq = catSeq;
    }

    public String getMerSeq() {
        return merSeq;
    }

    public void setMerSeq(String merSeq) {
        this.merSeq = merSeq;
    }
}
