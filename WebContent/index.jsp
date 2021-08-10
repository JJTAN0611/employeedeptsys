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
							<h5>
								<strong><u>O</u></strong>nline <strong><u>E</u></strong>mployee<strong>
									<u>D</u></strong>epartment<strong> <u>R</u></strong>egistration <strong>
									<u>S</u></strong>ystem</em>
							</h5>
							<br>
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
					<a href="MainServlet?target=department&action=view&keyword=">Click
						me to main department view</a>
					<hr>
					<form id="diform">
						<input type="hidden" name=target value="department" /> <input
							type="hidden" name=action value="getByIdAjax" /> <input
							type="text" id="id" name="id"
							placeholder="Department ID, eg. d009" value="" /> <input
							type="submit" id="disearch" value="Search" />
					</form>
					<div id='dioutput'></div>
				</div>
				<br>
				<div class="search" style="background-color: #ffffee">
					<h5>Search department by Name</h5>
					<a href="MainServlet?target=department&action=view&keyword=">Click
						me to main department view</a>
					<hr>
					<form id="dnform">
						<input type="hidden" name=target value="department" /> <input
							type="hidden" name=action value="getByNameAjax" /> <input
							type="text" id="text" name="name"
							placeholder="Department name, eg.Marketing" value="" /> <input
							type="submit" id="dnsearch" value="Search" />
					</form>
					<div id='dnoutput'></div>
				</div>
				<br>
			</div>
			<div class="col-lg-4 wow fadeInRight" data-wow-duration="0.5s"
				data-wow-delay="0.25s">
				<div class="search" style="background-color: #d9edf3">
					<h5>Search Department Employee by ID</h5>
					<a
						href="MainServlet?target=departmentemployee&action=view&keyword=">Click
						me to main department-employee view</a>
					<hr>
					<form id="deiform">
						<input type="hidden" name=target value="departmentemployee" /> <input
							type="hidden" name=action value="getByIdAjax" /> <input
							type="text" id="dept_id" name="dept_id"
							placeholder="Department ID, eg. d009" value="" /> <input
							type="number" id="emp_id" name="emp_id"
							placeholder="Employee ID, eg. 100001" value="" /><input
							type="submit" id="deisearch" value="Search" />
					</form>
					<div id='deioutput'></div>
				</div>
				<br>
			</div>
			<div class="col-lg-4 wow fadeInRight" data-wow-duration="0.5s"
				data-wow-delay="0.25s">
				<div class="search" style="background-color: #d6f8d6">
					<h5>Search employee by ID</h5>
					<a href="MainServlet?target=employee&action=view&keyword="
						onclick='$("#employee").trigger("click");'>Click me to main
						employee view</a>
					<hr>
					<form id="eiform">
						<input type="hidden" name=target value="employee" /> <input
							type="hidden" name=action value="getByIdAjax" /> <input
							type="number" id="id" name="id"
							placeholder="Employee ID, eg. 100001" value="" /> <input
							type="submit" id="eisearch" value="Search" />
					</form>
					<div id='eioutput'></div>
				</div>
				<br>
				<div class="search" style="background-color: #d6f8d6">
					<h5>Search employee by Name</h5>
					<a href="MainServlet?target=employee&action=view&keyword=">Click
						me to main employee view</a>
					<hr>
					<form id="enform">
						<input type="hidden" name=target value="employee" /> <input
							type="hidden" name=action value="getByNameAjax" /> <input
							type="text" id="id" name="name"
							placeholder="Employee Name, eg. Georgi Facello" value="" /> <input
							type="submit" id="ensearch" value="Search" />
					</form>
					<div id='enoutput'></div>
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
				<div class="item wow bounceInUp justify-content-center"  style="align-content: center" data-wow-duration="1s"
					data-wow-delay="0.3s">
					<a href="MainServlet?target=department&action=view">

						<div class="hidden-content">
							<h4>Department Record</h4>
							<p>Click to perform action on employee table. You may create report for each search.</p>
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
					<a
						href="MainServlet?target=departmentemployee&action=view&currentPage=1&keyword=">

						<div class="hidden-content">
							<h4>Department-Employee Record</h4>
							<p>Click to perform action on department-employee table. You may create report for each search.</p>
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
					<a href="MainServlet?target=employee&action=view&currentPage=1&keyword=">

						<div class="hidden-content">
							<h4>Employee Record</h4>
							<p>Click to perform action to employee table. You may create report for each search.</p>
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
					<p >
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
								href="tel:+601138100852">+6011-38100852</a></span>
						</h4>
					</div>
				</div>
			</div>
	
			<div class="col-lg-6 align-self-center wow fadeInLeft"
				data-wow-duration="0.5s" data-wow-delay="0.25s">
				<div class="section-heading">
					<br>
					<img class="shadow-lg" src="assets/images/demo.png"
						style="border-radius: 30px;" alt="Demo picture">
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>
<script>
	$("#disearch")
			.click(
					function() {
						$("#dioutput").empty();
						$
								.ajax({
									url : 'MainServlet',
									type : 'GET',
									dataType : 'json',
									data : $("#diform").serialize(),
									success : function(data) {
										if (data[0] == null) {
											alert("Invalid department id. Not exist!");
											$("#dioutput").html("");
										} else {
											$("#dioutput").append("<hr>");
											$("#dioutput").append(
													"<br>The department id: "
															+ data[0].id);
											$("#dioutput").append(
													"<br>The department name: "
															+ data[0].deptName);

										}
									},
									error : function() {
										alert('Invalid department id. Not exist. Error occur!');
										$("#dioutput").html("");
									}
								});
						return false;
					});

	$("#dnsearch")
			.click(
					function() {
						$("#dnoutput").empty();
						$
								.ajax({
									url : 'MainServlet',
									type : 'GET',
									dataType : 'json',
									data : $("#dnform").serialize(),
									success : function(data) {
										if (data[0] == null) {
											alert("Invalid department name. Not exist!");
											$("#dnoutput").html("");
										} else {
											$("#dnoutput")
													.append(
															"<hr>Please noted that only one result (The closest) is shown, please check the department name.<br><br>");
											$("#dnoutput").append(
													"<br>The department id: "
															+ data[0].id);
											$("#dnoutput").append(
													"<br>The department name: "
															+ data[0].deptName);

										}
									},
									error : function() {
										alert('Invalid department name. Not exist. Error occur!');
										$("#dnoutput").html("");
									}
								});
						return false;
					});

	$("#deisearch")
			.click(
					function() {
						$("#deioutput").empty();
						$
								.ajax({
									url : 'MainServlet',
									type : 'GET',
									dataType : 'json',
									data : $("#deiform").serialize(),
									success : function(data) {
										if (data[0] == null) {
											alert("Invalid department id and employee id. Not exist!");
											$("#deioutput").html("");
										} else {
											$("#deioutput").append("<hr>");
											$("#deioutput")
													.append(
															"<br>The department: "
																	+ data[0].department.id
																	+ " | "
																	+ data[0].department.deptName);
											$("#deioutput")
													.append(
															"<br>The employee: "
																	+ data[0].employee.id
																	+ " | "
																	+ data[0].employee.firstName
																	+ " "
																	+ data[0].employee.lastName
																	+ " | "
																	+ data[0].employee.gender);
											$("#deioutput").append(
													"<br>The position from date: "
															+ data[0].fromDate);
											$("#deioutput").append(
													"<br>The position to date: "
															+ data[0].toDate);

										}
									},
									error : function() {
										alert('Invalid department id and employee id. Not exist. Error occur!');
										$("#deioutput").html("");
									}
								});
						return false;
					});

	$("#eisearch").click(
			function() {
				$("#eioutput").empty();
				$.ajax({
					url : 'MainServlet',
					type : 'GET',
					dataType : 'json',
					data : $("#eiform").serialize(),
					success : function(data) {
						if (data[0] == null) {
							alert("Invalid employee id. Not exist!");
							$("#eioutput").html("");
						} else {
							$("#eioutput").append("<hr>");
							$("#eioutput").append(
									"<br>The employee id: " + data[0].id);
							$("#eioutput").append(
									"<br>The employee name: "
											+ data[0].firstName + " "
											+ data[0].lastName);

							$("#eioutput").append(
									"<br>The employee gender: "
											+ data[0].gender);
							$("#eioutput").append(
									"<br>The employee birth date: "
											+ data[0].hireDate);
							$("#eioutput").append(
									"<br>The employee hired date: "
											+ data[0].birthDate);
						}
					},
					error : function() {
						alert('Invalid employee id. Not exist. Error occur!');
						$("#eioutput").html("");
					}
				});
				return false;
			});

	$("#ensearch")
			.click(
					function() {
						$("#enoutput").empty();
						$
								.ajax({
									url : 'MainServlet',
									type : 'GET',
									dataType : 'json',
									data : $("#enform").serialize(),
									success : function(data) {
										console.log(data)
										if (data[0] == null) {
											alert("Invalid employee name. Not exist!");
											$("#enoutput").html("");
										} else {
											$("#enoutput")
													.append(
															"<hr>Please noted that only one result (The closest) is shown, please check the employee name.<br><br>");
											$("#enoutput").append(
													"<br>The employee id: "
															+ data[0].id);
											$("#enoutput").append(
													"<br>The employee name: "
															+ data[0].firstName
															+ " "
															+ data[0].lastName);

											$("#enoutput").append(
													"<br>The employee gender: "
															+ data[0].gender);
											$("#enoutput").append(
													"<br>The employee birth date: "
															+ data[0].hireDate);
											$("#enoutput")
													.append(
															"<br>The employee hired date: "
																	+ data[0].birthDate);
										}
									},
									error : function() {
										alert('Invalid employee name. Not exist. Error occur!');
										$("#enoutput").html("");
									}
								});
						return false;
					});
</script>
