// 页面加载
$(function(){
	$('#name').blur(function(){
		// 获取帐号文本框中填写的内容
		// 写法1
//		var userName = $('#txtUserName')[0].value;
		// 写法2
		var username = $(this).val();
		
		$.ajax({
			url: 'book',
			data: { param1: name },
			type: 'POST',
			error: function(data) {
				console.log("错误信息：" + data);
			},
			success: function(data) {
				$('#txtUserNameMsg')[0].innerText = data;

				// 对文本框、密码框等表单元素有效，对span等无效
//				$('#txtUserNameMsg').val(data);
			}
		});
	});
});
