package com.soft2242.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soft2242.shop.common.exception.ServerException;
import com.soft2242.shop.convert.AddressConvert;
import com.soft2242.shop.entity.UserShippingAddress;
import com.soft2242.shop.enums.AddressDefaultEnum;
import com.soft2242.shop.mapper.UserShippingAddressMapper;
import com.soft2242.shop.service.UserShippingAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft2242.shop.vo.AddressVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-10
 */
@Service
public class UserShippingAddressServiceImpl extends ServiceImpl<UserShippingAddressMapper, UserShippingAddress> implements UserShippingAddressService {

    @Override
    public Integer saveShippingAddress(AddressVO addressVO) {
        UserShippingAddress convert = AddressConvert.INSTANCE.convert(addressVO);

        if (addressVO.getIsDefault()== AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            List<UserShippingAddress> list= baseMapper.selectList(new LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getIsDefault, AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if (list.size()>0){
                throw new ServerException("已经存在默认地址，请勿重复操作");
            }
        }
        save(convert);
        return convert.getId();
    }

    @Override
    public Integer editShippingAddress(AddressVO addressVO) {
        UserShippingAddress userShippingAddress = baseMapper.selectById(addressVO.getId());
        if (userShippingAddress==null){
            throw new ServerException("地址不存在");
        }
        if (addressVO.getIsDefault()==AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            UserShippingAddress address = baseMapper.selectOne(new LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getUserId, addressVO.getUserId()).eq(UserShippingAddress::getIsDefault, AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if (address!=null){
                address.setIsDefault(AddressDefaultEnum.NOT_DEFAULT_ADDRESS.getValue().byteValue());
                updateById(address);
            }
        }
        UserShippingAddress address = AddressConvert.INSTANCE.convert(addressVO);
        updateById(address);
        return address.getId();
    }
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
   @Override
   public List<AddressVO> getList(Integer userId) {
       LambdaQueryWrapper<UserShippingAddress> wrapper = new LambdaQueryWrapper<>();
       wrapper.eq(UserShippingAddress::getUserId, userId);
//        根据是否为默认地址和创建时间倒序排列
       wrapper.orderByDesc(UserShippingAddress::getIsDefault);
       wrapper.orderByDesc(UserShippingAddress::getCreateTime);
       List<UserShippingAddress> list = baseMapper.selectList(wrapper);
       List<AddressVO> results = AddressConvert.INSTANCE.convertToAddressVOList(list);
       return results;
   }
    @Override
    public AddressVO getAddressInfo(Integer id) {
        UserShippingAddress userShippingAddress = baseMapper.selectById(id);
        if (userShippingAddress == null) {
            throw new ServerException("地址不存在");
        }
        AddressVO addressVO = AddressConvert.INSTANCE.convertToAddressVO(userShippingAddress);
        return addressVO;
    }
    @Override
    public void removeShippingAddress(Integer id) {
        removeById(id);
    }

}
