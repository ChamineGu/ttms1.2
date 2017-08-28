package cn.tedu.ttms.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
/**通过此注解声明此类为一个全局异常处理类型*/
@ControllerAdvice
public class ControllerExceptionHandler {
	/**当spring发现系统出现异常时,且异常的
	 * 类型为ServiceException类型,此时
	 * 回调此方法,并将异常值传递给这个方法
	 * 我们就可以在此方法中对业务异常进行统一
	 * 处理,列如封装到jsonResult中,然后
	 * 写到客户端告诉用户*/
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public JsonResult handlerServiceException(
			ServiceException e){
		e.printStackTrace();
		//将异常封装到JsonResult
		return new JsonResult(e);
		//this.state=ERROR
		//this.message=e.getMessage();
	}
}
