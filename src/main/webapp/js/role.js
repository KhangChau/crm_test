$(document).ready(function(){
	//logic code
	
	//alert("Test")
	
	$(".btn-xoa").click(function(){
		//alert("Test")
		
		var id = $(this).attr("id-role") //this nay la cua button click btn-xoa
		var This = $(this)
		
		//gọi đường dãn và lấy dữ liệu từ đường dẫn đó trả ra
		$.ajax({
		  method: "DELETE",
		  url: "http://localhost:8080/helloservlet/api/role?id="+id, //xoa database tai day
		  //data: { name: "John", location: "Boston" } -> chỉ dành cho phương thức POST
		})
		  .done(function( result ) {
			  if(result.data == true){
				  This.closest("tr").remove() // neu o day la ${this} thi no se hieu la cua function 'done'
			  }
		    console.log(result)
		  });
	})
})