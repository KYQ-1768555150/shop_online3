package com.soft2242.shop.controller;

import com.soft2242.shop.common.result.Result;
import com.soft2242.shop.entity.Category;
import com.soft2242.shop.entity.IndexCarousel;
import com.soft2242.shop.entity.User;
import com.soft2242.shop.query.UserLoginQuery;
import com.soft2242.shop.service.CategoryService;
import com.soft2242.shop.service.IndexCarouselService;
import com.soft2242.shop.service.IndexRecommendService;
import com.soft2242.shop.service.UserService;
import com.soft2242.shop.vo.IndexRecommendVO;
import com.soft2242.shop.vo.LoginResultVO;

import com.soft2242.shop.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.soft2242.shop.common.utils.ObtainUserIdUtils.getUserId;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ycshang
 * @since 2023-11-10
 */
@Tag(name = "用户模块")
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "微信登录")
    @PostMapping("login/wxMin")
    public Result<LoginResultVO> wxLogin(@RequestBody @Validated UserLoginQuery query) {
        LoginResultVO userVO = userService.login(query);
        return Result.ok(userVO);
    }
    @Operation(summary = "用户详情")
    @GetMapping("/profile")
    private Result<User> getUserInfo(HttpServletRequest request) {
        Integer userId = getUserId(request);
        User userInfo = userService.getUserInfo(userId);
        return Result.ok(userInfo);
    }
    @Operation(summary = "修改用户信息")
    @PutMapping("/profile")
    private Result<UserVO> editUserInfo(HttpServletRequest request, @RequestBody @Validated UserVO userVO) {
        Integer userId = getUserId(request);
        userVO.setId(userId);
        UserVO userInfo = userService.editUserInfo(userVO);
        return Result.ok(userInfo);
    }
    @Operation(summary = "修改用户头像")
    @PostMapping("/profile/avatar")
    private Result<String> editUserAvatar(HttpServletRequest request, MultipartFile file) {
        Integer userId = getUserId(request);
        String uploadFileName = userService.editUserAvatar(userId, file);
        return Result.ok(uploadFileName);
    }
    @Tag(name = "首页管理")
    @RestController
    @RequestMapping("home")
    @AllArgsConstructor
    public class IndexCarouselController {
        private final IndexCarouselService indexCarouselService;

        @Operation(summary = "首页/商品分类页-推荐轮播图")
        @GetMapping("banner")
        public Result<List<IndexCarousel>> getList(@RequestParam Integer distributionSite) {

            List<IndexCarousel> list = indexCarouselService.getList(distributionSite);
            return Result.ok(list);

        }
    }

    @Tag(name = "推荐管理")
    @RestController
    @RequestMapping("home")
    @AllArgsConstructor
    public class IndexRecommendController {
        private final IndexRecommendService indexRecommendService;

        @Operation(summary = "首页-热门推荐")
        @GetMapping("/hot")
        public Result<List<IndexRecommendVO>> getList() {
            List<IndexRecommendVO> list = indexRecommendService.getList();
            return Result.ok(list);

        }

    }
}