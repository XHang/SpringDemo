package com.springaop.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 切面类
 * @author Administrator
 *
 */
@Component
@Aspect
public class AspectDemo {
	/**
	 * <pre>
	 * 第一个示例：不需要定义切面表达式，直接写切面处理方法
	 * 这个处理方法是环绕通知。需要满足一下条件才可以作为环绕通知的方法
	 * 1：加@Around注解，括号里面写切面表达式
	 * 2：形参：ProceedingJoinPoint proceedingJoinPoint
	 * 3：至少有一个返回值。可以是Object，接受proceed方法的返回值，并返回出去。
	 * 		并不做强制要求，但是如果没有返回值，也就意味着原代理的返回值没有用到。
	 * </pre>
	 * @param proceedingJoinPoint AOP执行代理时传来的切面对象
	 * @return 原代理方法的返回值
	 * @throws Throwable 如果原代理方法抛出异常
	 */
	@Around("execution(public * com.springaop.service.*.add(..))")
	public void  around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("前缀通知");
		Method method=getMethod(proceedingJoinPoint);
		System.out.println("当前代理方法的方法名是："+method.getName());
		System.out.println("当前代理方法所在的类名是："+getClazz(proceedingJoinPoint).getName());
		 System.out.println("当前请求的url地址是："+getRequest().getRequestURI());
		proceedingJoinPoint.proceed();
		System.out.println("后缀通知");
	}
	
	/**
	 * <pre>
	 * 	第二个示例，怎么使用切点对象，就是proceedingJoinPoint这货
	 * 	接下来的方法将演示如何从proceedingJoinPoint取得被代理方法的方法对象
	 * </pre>
	 * @return
	 */
	public Method getMethod(ProceedingJoinPoint proceedingJoinPoint){
		Signature signature=proceedingJoinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature)signature;  
		Method method=methodSignature.getMethod();
		return  method;
	}
	/**
	 * 第三个示例，怎么利用proceedingJoinPoint取得实际传来的参数
	 * so easy
	 * @param proceedingJoinPoint
	 * @return
	 */
	public Object[]  getArgs(ProceedingJoinPoint proceedingJoinPoint){
		return proceedingJoinPoint.getArgs();
	}
	/**
	 * 第四个示例，如何取得被代理对象所在的类的classs。
	 */
	public Class<?> getClazz(ProceedingJoinPoint proceedingJoinPoint){
		Class<?> clazz=proceedingJoinPoint.getSignature().getDeclaringType();
		return clazz;
	}
	/**
	 * 第五个示例，如果调用该方法的线程是通过web环境建立的，则可以获取到request
	 * @return
	 */
	public HttpServletRequest   getRequest(){
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return request;
	}
	
}
