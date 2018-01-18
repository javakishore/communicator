
/*Star left Navigation*/
	function toggle_visibility(id) {
    var e = document.getElementById(id);
    var myClasses = document.querySelectorAll('.sidenav'),
    i = 0,
    l = myClasses.length;

for (i; i < l; i++) {
    myClasses[i].style.display = 'none';
}
    if (e.style.display == 'none') e.style.display = 'block';
    
}
/*End left Navigation*/
/*Star left Navigation  cros icon close*/
function open_Nav() {
    document.getElementById("FinanceProcess").style.width = "250px";
}

function close_Nav() {
    document.getElementById("FinanceProcess").style.width = "0";
}

function Businessopen_Nav() {
    document.getElementById("BusinessTeam").style.width = "250px";
}

function Businessclose_Nav() {
    document.getElementById("BusinessTeam").style.width = "0";
}

function NewLearningopen_Nav() {
    document.getElementById("NewLearning").style.width = "250px";
}

function NewLearningclose_Nav() {
    document.getElementById("NewLearning").style.width = "0";
}

function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}
/*End left Navigation  cros icon close*/

/*Star image silder for Courses Recommended for you*/
$(document).ready(function(){
  // Add smooth scrolling to all links in navbar + footer link
  $(".navbar a, footer a[href='#myPage']").on('click', function(event) {
    // Make sure this.hash has a value before overriding default behavior
    if (this.hash !== "") {
      // Prevent default anchor click behavior
      event.preventDefault();

      // Store hash
      var hash = this.hash;

      // Using jQuery's animate() method to add smooth page scroll
      // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
      $('html, body').animate({
        scrollTop: $(hash).offset().top
      }, 900, function(){
   
        // Add hash (#) to URL when done scrolling (default click behavior)
        window.location.hash = hash;
      });
    } // End if
  });
  
  $(window).scroll(function() {
    $(".slideanim").each(function(){
      var pos = $(this).offset().top;

      var winTop = $(window).scrollTop();
        if (pos < winTop + 600) {
          $(this).addClass("slide");
        }
    });
  });
})
/*Star image silder for Courses Recommended for you*/
