package com.atguigu.gmall.search;

import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import com.atguigu.gmall.search.feign.GmallPmsClient;
import com.atguigu.gmall.search.feign.GmallWmsClient;
import com.atguigu.gmall.search.vo.GoodsVO;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
class GmallSearchApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private JestClient jestClient;


    @Test
    public void create() throws IOException {

        Index index = new Index.Builder(new User("蔡宁", "123456", 26)).index("user").type("info").id("1").build();

        DocumentResult result = jestClient.execute(index);

    }

    @Test
    public void update() throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("doc", new User("蔡杰", "123456", 36));

        Update update = new Update.Builder(map).index("user").type("info").id("1").build();
        DocumentResult result = jestClient.execute(update);
        System.out.println(result.toString());
    }

    @Test
    public void search() throws IOException {


        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}";

        Search action = new Search.Builder(query).addIndex("user").addType("info").build();

        SearchResult execute = jestClient.execute(action);


        System.out.println(execute.getSourceAsObject(User.class, false));
    }

    @Autowired
    private GmallPmsClient gmallPmsClient;

    @Autowired
    private GmallWmsClient gmallWmsClient;

    @Test
    public void importData() throws Exception {

        //初始化分页参数
        Long pageNum = 1l;
        Long pageSize = 100l;

        do {

            //分页查询已上架的SPU信息
            QueryCondition condition = new QueryCondition();
            condition.setPage(pageNum);//设置页码
            condition.setLimit(pageSize);//设置每页大小
            Resp<List<SpuInfoEntity>> listResp = this.gmallPmsClient.querySpuPage(condition);
            //获取当前页数据的spuInfo数据
            List<SpuInfoEntity> spuInfoEntities = listResp.getData();

            //遍历spu获取到spu下的所有水库并导入到索引库中
            for (SpuInfoEntity spuInfoEntity : spuInfoEntities) {//遍历spu
                Resp<List<SkuInfoEntity>> skuResp = this.gmallPmsClient.querySkuBySpuId(spuInfoEntity.getId());

                List<SkuInfoEntity> skuInfoEntities = skuResp.getData();

                if (CollectionUtils.isEmpty(skuInfoEntities)) {
                    continue;
                }

                for (SkuInfoEntity skuInfoEntity : skuInfoEntities) {
                    GoodsVO goodsVO = new GoodsVO();

                    // 设置sku相关数据
                    goodsVO.setName(skuInfoEntity.getSkuTitle());
                    goodsVO.setId(skuInfoEntity.getSkuId());
                    goodsVO.setPic(skuInfoEntity.getSkuDefaultImg());
                    goodsVO.setPrice(skuInfoEntity.getPrice());
                    goodsVO.setSale(100);//销量
                    goodsVO.setSort(0);// 综合排序

                    // 设置品牌相关的
                    Long brandId = skuInfoEntity.getBrandId();
                    Resp<BrandEntity> brandEntityResp = this.gmallPmsClient.queryBrandById(brandId);
                    BrandEntity brandEntity = brandEntityResp.getData();
                    if (brandEntity != null) {
                        goodsVO.setBrandId(skuInfoEntity.getBrandId());
                        goodsVO.setBrandName(brandEntity.getName());
                    }

                    // 设置分类相关的
                    Resp<CategoryEntity> categoryEntityResp = this.gmallPmsClient.queryCategoryById(skuInfoEntity.getCatalogId());
                    CategoryEntity categoryEntity = categoryEntityResp.getData();
                    if (categoryEntity != null) {
                        goodsVO.setProductCategoryId(skuInfoEntity.getCatalogId());
                        goodsVO.setProductCategoryName(categoryEntity.getName());
                    }

                    // 设置搜索属性
                    Resp<List<SpuAttributeValueVO>> searchAttrValueResp = this.gmallPmsClient.querySearchAttrValue(spuInfoEntity.getId());
                    List<SpuAttributeValueVO> spuAttributeValueVOList = searchAttrValueResp.getData();
                    goodsVO.setAttrValueList(spuAttributeValueVOList);

                    // 设置库存
                    Resp<List<WareSkuEntity>> resp = this.gmallWmsClient.queryWareBySkuId(skuInfoEntity.getSkuId());
                    List<WareSkuEntity> wareSkuEntities = resp.getData();
                    if (wareSkuEntities.stream().anyMatch(t -> t.getStock() > 0)) {
                        goodsVO.setStock(1l);
                    } else {
                        goodsVO.setStock(0l);
                    }

                    Index index = new Index.Builder(goodsVO).index("goods").type("info").id(skuInfoEntity.getSkuId().toString()).build();

                    try {
                        this.jestClient.execute(index);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //获取当前页的记录数
            pageSize = Long.valueOf(spuInfoEntities.size());//获取当前页的记录数

            pageNum++;//设置下一页
        } while (pageSize == 100);//循环条件
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    private String name;
    private String password;
    private Integer age;

}