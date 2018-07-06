package com.qiyue.miaosha.web;



import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qiyue.miaosha.dto.Exposer;
import com.qiyue.miaosha.dto.SecKillExecution;
import com.qiyue.miaosha.dto.SeckillResult;
import com.qiyue.miaosha.enums.SeckillStatEnum;
import com.qiyue.miaosha.exception.RepeatKillException;
import com.qiyue.miaosha.exception.SecKillCloseException;
import com.qiyue.miaosha.pojo.SecKill;
import com.qiyue.miaosha.service.SecKillService;

@Controller
@RequestMapping("/seckill")
public class SecKillController {

	private final Logger logger=LoggerFactory.getLogger(SecKillController.class);
	@Autowired
	private SecKillService secKillService;
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		List<SecKill> list=secKillService.getSecKillList();
		model.addAttribute("list", list);
		return "list";
	}
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model){
		if(seckillId==null){
			return "redirect:/seckill/list";
		}
		SecKill seckill=secKillService.getById(seckillId);
		if(seckill==null){
			return "redirect:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	@RequestMapping(value="/{seckillId}/exposer",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
		SeckillResult<Exposer> result;
		try{
			Exposer exposer=secKillService.exportSeckillUrl(seckillId);
			result=new SeckillResult<Exposer>(true, exposer);
		}catch(Exception e){
			result=new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}
	@RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
	@ResponseBody  
    public SeckillResult<SecKillExecution> execute(@PathVariable("seckillId")Long seckillId,  
                                                   @PathVariable("md5")String md5,   
                                                   @CookieValue(value = "killPhone", required = false)Long phone){  
        if(phone == null){  
            return new SeckillResult<>(false, "未注册");  
        }  
          
        try {  
             SecKillExecution execution = secKillService.executeSeckill(seckillId, phone, md5);  
            return new SeckillResult<SecKillExecution>(true, execution);  
        } catch (SecKillCloseException e) {  
        	SecKillExecution execution = new SecKillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);  
            return new SeckillResult<SecKillExecution>(false, execution);  
        } catch (RepeatKillException e) {  
        	SecKillExecution execution = new SecKillExecution(seckillId, SeckillStatEnum.END);  
            return new SeckillResult<SecKillExecution>(false, execution);  
        } catch (Exception e) {  
        //    logger.error(e.getMessage(), e);  
        	System.out.println(e);
            SecKillExecution execution = new SecKillExecution(seckillId, SeckillStatEnum.INNER_ERROR);  
            return new SeckillResult<SecKillExecution>(false, execution);  
        }  
    }  
      
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)  
    @ResponseBody  
    public SeckillResult<Long> time(){  
        Date now = new Date();  
        return new SeckillResult<>(true, now.getTime());  
    }  
}
