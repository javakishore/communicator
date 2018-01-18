var myMessages = ['info','warning','error','success']; // define the messages types    	 
function hideAllMessages()
{
		 var messagesHeights = new Array(); // this array will store height for each
	 
		 for (i=0; i<myMessages.length; i++)
		 {
				  messagesHeights[i] = $('.' + myMessages[i]).outerHeight();
				  $('.' + myMessages[i]).css('top', -messagesHeights[i]); //move element outside viewport	  
		 }
}

function showMessage(type)
{
	$('.'+ type +'-trigger').click(function(){
		  hideAllMessages();				  
		  $('.'+type).animate({top:"0"}, 500);
	});
}

$(document).ready(function(){
		 
		 // Initially, hide them all
		 hideAllMessages();
		 
		 // Show message
		 for(var i=0;i<myMessages.length;i++)
		 {
			showMessage(myMessages[i]);
		 }
		 
		 // When message is clicked, hide it
		 $('.message').click(function(){			  
				  $(this).animate({top: -$(this).outerHeight()}, 500);
		  });		 
		 
		 
});   

$(document).ready(function(){
  $(".Policy_sub_menues").click(function(){
  $(".Policy_submenu").toggle(100);
  }); 
  
  $(".Member_sub_menues").click(function(){
  $(".Member_submenu").toggle(100);
  }); 
});    
 
						

$(document).ready(function(){
$(".toggle_Pannel_One").hide();
  $(".Pannel_One").click(function(){
  $(".toggle_Pannel_One").toggle(100);
  }); 
  
 
 
  $(".toggle_Pannel_Two").hide();
  $(".Pannel_Two").click(function(){
  $(".toggle_Pannel_Two").toggle(100);
  }); 
  
  $(".toggle_Pannel_Three").hide();
  $(".Pannel_Three").click(function(){
  $(".toggle_Pannel_Three").toggle(100);
  }); 
  
 

  $(".toggle_Pannel_Four").hide();
  $(".Pannel_Four").click(function(){
  $(".toggle_Pannel_Four").toggle(100);
  }); 
  
 
 
  $(".toggle_Pannel_Five").hide();
  $(".Pannel_Five").click(function(){
  $(".toggle_Pannel_Five").toggle(100);
  }); 
  
  $(".toggle_Pannel_Six").hide();
  $(".Pannel_Six").click(function(){
  $(".toggle_Pannel_Six").toggle(100);
  }); 
  
  
$(".toggle_Pannel_Seven").hide();
  $(".Pannel_Seven").click(function(){
  $(".toggle_Pannel_Seven").toggle(100);
  }); 
  
$(".toggle_Pannel_Eight").hide();
  $(".Pannel_Eight").click(function(){
  $(".toggle_Pannel_Eight").toggle(100);
  });   
  
  $(".toggle_Pannel_Nine").hide();
  $(".Pannel_Nine").click(function(){
  $(".toggle_Pannel_Nine").toggle(100);
  });   
  
   $(".toggle_Pannel_Ten").hide();
  $(".Pannel_Ten").click(function(){
  $(".toggle_Pannel_Tene").toggle(100);
  });  
  
});   


