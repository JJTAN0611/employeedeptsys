
<footer>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 wow fadeIn" data-wow-duration="1s"
				data-wow-delay="0.25s">
				<p>
					© Copyright 2021 <b>OEDRS</b> All Rights Reserved.
			</div>
		</div>
	</div>
</footer>
<!-- Scripts -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/owl-carousel.js"></script>
<script src="assets/js/animation.js"></script>
<script src="assets/js/imagesloaded.js"></script>
<script src="assets/js/templatemo-custom.js"></script>

<script>
	window.onload = function() {
		var x = readCookie('oedrsUserId');
		document.getElementById("userId").innerHTML = x;
		console.log("ID" + x);
<%String z = request.getHeader("user-agent");
			if (!(z.contains("Chrome"))&&!((boolean)request.getSession().getAttribute("browserNotified"))) {
				request.getSession().setAttribute("browserNotified",true);
			%>
	alert("Optimised view is in Chrome browser!");
<%}%>
	}
	function readCookie(name) {
		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1, c.length);
			if (c.indexOf(nameEQ) == 0)
				return c.substring(nameEQ.length, c.length);
		}
		return null;
	}
</script>

</body>
</html>