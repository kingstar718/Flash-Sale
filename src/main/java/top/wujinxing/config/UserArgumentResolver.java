package top.wujinxing.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.wujinxing.entity.User;
import top.wujinxing.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wujinxing
 * date 2019 2019/7/23 11:12
 * description
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserService userService;

    //参数类型为User时才做处理
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //获取参数类型
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, UserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) return null;

        String token = StringUtils.isEmpty(paramToken)? cookieToken: paramToken;
        return userService.getByToken(response, token);
    }

    //遍历所有cookie，找到需要的那个cookie
    private String getCookieValue(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies==null||cookies.length<=0) return null;
        for (Cookie cookie: cookies){
            if (cookie.getName().equals(cookieName)) return cookie.getValue();
        }
        return null;
    }
}
