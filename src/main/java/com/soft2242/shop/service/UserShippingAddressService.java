package com.soft2242.shop.service;

import com.soft2242.shop.entity.UserShippingAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft2242.shop.vo.AddressVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-10
 */
public interface UserShippingAddressService extends IService<UserShippingAddress> {

    Integer saveShippingAddress(AddressVO addressVO);
    Integer editShippingAddress(AddressVO addressVO);

    /* @Override
         public List<AddressVO> getAllShippingAddresses() {
             List<UserShippingAddress> userAddresses = baseMapper.selectList(null);

             List<AddressVO> addressVOList = new ArrayList<>();

             for (UserShippingAddress userAddress : userAddresses) {
                 AddressVO addressVO = AddressConvert.INSTANCE.convertToAddressVO(userAddress);
                 addressVOList.add(addressVO);
             }

             return addressVOList;
         }
         @Override
         public String deleteShippingAddress(Integer addressId) {
             UserShippingAddress userAddress = baseMapper.selectById(addressId);

             if (userAddress == null) {
                 throw new RuntimeException("Address not found");
             }

             baseMapper.deleteById(addressId);
             return "删除成功";
         }

         @Override
         public AddressVO detailShippingAddress(Integer addressId) {
             UserShippingAddress userAddress = baseMapper.selectById(addressId);
             if (userAddress==null){
                 throw new ServerException("该地址为空，请重新输入地址id");
             }
             AddressVO addressVO = AddressConvert.INSTANCE.convertToAddressVO(userAddress);
             return addressVO;
         }*/
    List<AddressVO> getList(Integer userId);

    AddressVO getAddressInfo(Integer id);

    void removeShippingAddress(Integer id);
   /* List<AddressVO> getAllShippingAddresses();

    String deleteShippingAddress(Integer addressId);
    AddressVO detailShippingAddress(Integer addressId);*/
}
