package com.soft2242.shop.service;

import com.soft2242.shop.entity.UserShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft2242.shop.query.CartQuery;
import com.soft2242.shop.query.EditCartQuery;
import com.soft2242.shop.vo.CartGoodsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-10
 */
public interface UserShoppingCartService extends IService<UserShoppingCart> {

    CartGoodsVO addShopCart(CartQuery query);
    List<CartGoodsVO> shopCartList(Integer userId);

    CartGoodsVO editCart(EditCartQuery query);

    void removeCartGoods(Integer userId, List<Integer> ids);

    void editCartSelected(Boolean selected, Integer userId);
}
