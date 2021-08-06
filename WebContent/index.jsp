<%@ page errorPage="error.jsp"%>
<%@ include file="header.jsp"%>
<div></div>

<div class="main-banner wow fadeIn top-first" id="top"
	data-wow-duration="1s" data-wow-delay="0.5s">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="row">
					<div class="col-lg-6 align-self-center">
						<div class="left-content header-text wow fadeInLeft"
							data-wow-duration="1s" data-wow-delay="1s">
							<h2>
								Welcome To <em>O</em><span>ED</span><em>RS</em>
							</h2>
							<p>
								<strong><u>O</u></strong>nline <strong><u>E</u></strong>mployee<strong>
									<u>D</u>
								</strong>epartment<strong> <u>R</u></strong>egistration <strong><u>S</u></strong>ystem</em>
							</p>
							<div class="main-red-button">
								<a href="#functionality">Go explore</a>
							</div>
							<br>
							<div class="main-blue-button">
								<a href="#quicksearch">Quick Search</a>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="right-image wow fadeInRight" data-wow-duration="1s"
							data-wow-delay="0.5s">
							<img src="assets/images/banner-right-image.png"
								alt="team meeting">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="quicksearch" class="section">
	<div class="container">
		<div class="row">
			<br> <br> <br>
			<hr>
			<div class="section-heading  wow bounceIn" data-wow-duration="1s"
				data-wow-delay="0.2s">
				<h2 class="text-center">
					<em>Quick</em><span> Search</span>
				</h2>
				<br>
			</div>
		</div>
		<div class="row">

			<div class="col-lg-4 wow fadeInRight" data-wow-duration="0.5s"
				data-wow-delay="0.25s">
				<div class="search" style="background-color: #ffffee">
					<h5>Search department by ID</h5>
					<hr>
					<form id="dform">
						<input type="hidden" name=target value="department" /> <input
							type="hidden" name=action value="ajax" /> <input type="text"
							id="id" name="id" placeholder="Department ID" value="" /> <input
							type="submit" id="dsearch" value="Search" />
					</form>
					<div id='doutput'></div>
				</div>
				<br>
			</div>
			<div class="col-lg-4 wow fadeInRight" data-wow-duration="0.5s"
				data-wow-delay="0.25s">
				<div class="search" style="background-color: #d9edf3">
					<h5>Search Department Employee by ID</h5>
					<hr>
					<form id="deform">
						<input type="hidden" name=target value="departmentemployee" /> <input
							type="hidden" name=action value="ajax" /> <input type="text"
							id="dept_id" name="dept_id" placeholder="Department ID" value="" />
						<input type="number" id="emp_id" name="emp_id"
							placeholder="Employee ID" value="" /><input type="submit"
							id="desearch" value="Search" />
					</form>
					<div id='deoutput'></div>
				</div>
				<br>
			</div>
			<div class="col-lg-4 wow fadeInRight" data-wow-duration="0.5s"
				data-wow-delay="0.25s">
				<div class="search" style="background-color: #d6f8d6">
					<h5>Search employee by ID</h5>
					<hr>
					<form id="eform">
						<input type="hidden" name=target value="employee" /> <input
							type="hidden" name=action value="ajax" /> <input type="number"
							id="id" name="id" placeholder="Employee ID" value="" /> <input
							type="submit" id="esearch" value="Search" />
					</form>
					<div id='eoutput'></div>
				</div>
				<br>
			</div>
		</div>
	</div>
</div>

<div id="functionality" class="our-functionality section">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 offset-lg-3">
				<div class="section-heading  wow bounceIn" data-wow-duration="1s"
					data-wow-delay="0.2s">
					<h2>
						Click the <em>Icon</em> below <span> to navigate to each
							function</span>
					</h2>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4 col-sm-6">
				<div class="item wow bounceInUp" data-wow-duration="1s"
					data-wow-delay="0.3s">
					<a href="#" onclick='$("#department").trigger("click");'>

						<div class="hidden-content">
							<h4>Department Record</h4>
							<p>Click to perform action on employee table</p>
						</div>
						<div class="showed-content">
							<img src="assets/images/department.png" alt="">
						</div>
					</a>
				</div>
			</div>
			<div class="col-lg-4 col-sm-6">
				<div class="item wow bounceInUp" data-wow-duration="1s"
					data-wow-delay="0.4s">
					<a href="#" onclick='$("#department_employee").trigger("click");'>

						<div class="hidden-content">
							<h4>Department-Employee Record</h4>
							<p>Click to perform action on department-employee table</p>
						</div>
						<div class="showed-content">
							<img src="assets/images/departmentemployee.png" alt="">
						</div>
					</a>
				</div>
			</div>
			<div class="col-lg-4 col-sm-6">
				<div class="item wow bounceInUp" data-wow-duration="1s"
					data-wow-delay="0.5s">
					<a href="#" onclick='$("#employee").trigger("click");'>

						<div class="hidden-content">
							<h4>Employee Record</h4>
							<p>Click to perform action to employee table.</p>
						</div>
						<div class="showed-content">
							<img src="assets/images/employee.png" alt="">
						</div>
					</a>
				</div>
				<br>
			</div>
		</div>
	</div>
</div>

<div id="contact" class="contact-us section">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 align-self-center wow fadeInLeft"
				data-wow-duration="0.5s" data-wow-delay="0.25s">
				<div class="section-heading">
					<h2>Feel Free To Send Me A Message When You Need</h2>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This
						web application proposes the development of an online employee
						department registration system using Servlet, Java Server Page
						(JSP), Session Bean technologies, and relational database. This
						web application incorporates object-oriented principles into a
						3-tier architecture. <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The
						main function of this system is to allow administrators to
						register employee details and assign them into different
						departments. This system is used to track all the employee and
						department relation records. <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beside
						the create, read, update, and delete (CRUD) function, this system
						integrates several functionalities such as searching, sorting,
						pagination to improve user experience. The result can be tabulated
						according to the actual need. <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Moreover,
						the generate report function is also provided to the administrator
						for managing purposes. <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last
						but not least, an application-level analysis is carried out in the
						system. The UML diagrams which indicate the overall view of the
						system design are illustrated in this poster.
					</p>
					<div class="phone-info">
						<h4>
							For any enquiry, Call:<span><i class="fa fa-phone"></i> <a
								href="#">+6011-38100852</a></span>
						</h4>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>
<script>
	$("#dsearch").click(
			function() {
				$("#doutput").empty();
				$.ajax({
					url : 'MainServlet',
					type : 'GET',
					dataType : 'json',
					data : $("#dform").serialize(),
					success : function(data) {
						if (data[0]==null) {
							alert("Invalid department id");
						} else {
							$("#doutput").append("<hr>");
							$("#doutput").append(
									"<br>The department id: " + data[0].id);
							$("#doutput").append(
									"<br>The department name: "
											+ data[0].deptName);

						}
					},
					error : function() {
						alert('Invalid department id');
					}
				});
				return false;
			});

	$("#desearch").click(
			function() {
				$("#deoutput").empty();
				$.ajax({
					url : 'MainServlet',
					type : 'GET',
					dataType : 'json',
					data : $("#deform").serialize(),
					success : function(data) {
						if (data[0] == null) {
							alert("Invalid department id and employee id");
						} else {
							$("#deoutput").append("<hr>");
							$("#deoutput").append(
									"<br>The department: "
											+ data[0].department.id + " | "
											+ data[0].department.deptName);
							$("#deoutput").append(
									"<br>The employee: " + data[0].employee.id
											+ " | "
											+ data[0].employee.firstName + " "
											+ data[0].employee.lastName + " | "
											+ data[0].employee.gender);
							$("#deoutput").append(
									"<br>The position from date: "
											+ data[0].fromDate);
							$("#deoutput").append(
									"<br>The position to date: "
											+ data[0].toDate);

						}
					},
					error : function() {
						alert('Invalid department id and employee id');
					}
				});
				return false;
			});
	$("#esearch").click(
			function() {
				$("#eoutput").empty();
				$.ajax({
					url : 'MainServlet',
					type : 'GET',
					dataType : 'json',
					data : $("#eform").serialize(),
					success : function(data) {
						if (data[0] == null) {
							alert("Invalid employee id");
						} else {
							$("#eoutput").append("<hr>");
							$("#eoutput").append(
									"<br>The employee id: " + data[0].id);
							$("#eoutput").append(
									"<br>The employee name: "
											+ data[0].firstName + " "
											+ data[0].lastName);

							$("#eoutput").append(
									"<br>The employee gender: "
											+ data[0].gender);
							$("#eoutput").append(
									"<br>The employee birth date: "
											+ data[0].hireDate);
							$("#eoutput").append(
									"<br>The employee hired date: "
											+ data[0].birthDate);
						}
					},
					error : function() {
						alert('Invalid employee id');
					}
				});
				return false;
			});
</script>
