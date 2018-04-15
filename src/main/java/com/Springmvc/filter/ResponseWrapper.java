package com.Springmvc.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * response的包装类，实现该包装类的目的，是为了能够取出response输出数据里面的内容
 * 其原理是通过覆写response的两个重要的对象，printlnWriter和OutputStream。
 * 自定义我们自己的打印流和输出流。
 * 最终覆写里面的写入方法，当调用者调用写入方法时，将数据存在两个流里面的一个成员数组里面。
 *
 * 不过要想生效，还需要将这个响应对象在过滤链条中，偷梁换柱，把原先的response对象，替换为该类对象。
 * 方可大功告成
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response
     * @throws IllegalArgumentException if the response is null
     */
    public ResponseWrapper(HttpServletResponse response) {
        super(response);

    }


}
