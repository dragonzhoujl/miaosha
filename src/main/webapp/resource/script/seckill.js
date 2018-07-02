//存放主要交互逻辑的js代码
//javascript模块化(package,类。方法)
var seckill={
	//封装秒杀相关ajax的url
	URL:{
		now: function(){
			return '/Seckill/seckill/time/now';
		},
		exposer:function(seckillId){
			return 'Seckill/seckill/'+seckillId+"/exposer";
		},
		execution: function(seckillId,md5){
			return '/Seckill/seckill/'+seckillId+"/"+md5+"/execution";
		}
	},
	
	//验证手机号
	validatePhone: function(phone){
		if(phone&&phone.length==11&&!isNaN(phone)){
			return true;//直接判断对象会看是否为空，空就是undefine就是false;isNaN非数字返回true
		}else{
			return false;
		}
	},
	//详情页秒杀逻辑
	detail:{
		//详情页初始化
		init: function(params){
			//手机验证和登录，计时交互
			//规划我们的交互流程
			//在cookie中查找手机号
			var killPhone=$.cookie("killPhone");
			//验证手机号
			if(!seckill.validatePhone(killPhone)){
				//绑定手机 控制输出
				var killPhoneModal=$("#killPhoneModal");
				killPhoneModal.modal({
					show:true,//显示弹出层
					backdrop:'static',//禁止位置关闭
					keyboard:false//关闭键盘事件
				});
				$("#killPhoneBtn").click(function (){
					var inputPhone=$('#killPhoneKey').val();
					console.log("inputPhone: "+inputPhone);
					if(seckill.validatePhone(inputPhone)){
						//电话写入cookie(7天过期)
						$.cookie('killPhone',inputPhone,{expires:7,path:'/Seckill'});
						//验证通过 刷新页面
						window.location.reload();
					}else{
						//todo错误文案信息抽取到前端字典里
						$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
					}
				});
			}
			//已经登录
			0
		}
	}
		
		
}