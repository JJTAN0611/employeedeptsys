<%@ page errorPage = "error.jsp" %>
<%@ include file="header.jsp" %>  
<div></div>

  <div class="main-banner wow fadeIn top-first" id="top" data-wow-duration="1s" data-wow-delay="0.5s">
    <div class="container">
      <div class="row">
        <div class="col-lg-12">
          <div class="row">
            <div class="col-lg-6 align-self-center">
              <div class="left-content header-text wow fadeInLeft" data-wow-duration="1s" data-wow-delay="1s">
                <h2>Welcome To <em>O</em><span>ED</span><em>RS</em></h2>
                <p> <strong><u>O</u></strong>nline <strong><u>E</u></strong>mployee<strong> <u>D</u></strong>epartment<strong> <u>R</u></strong>egistration <strong><u>S</u></strong>ystem</em></p>
                <div class="main-red-button"><a href="#functionality">Go explore</a></div>
              </div>
            </div>
            <div class="col-lg-6">
              <div class="right-image wow fadeInRight" data-wow-duration="1s" data-wow-delay="0.5s">
                <img src="assets/images/banner-right-image.png" alt="team meeting">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  
  <div id="functionality" class="our-functionality section">
    <div class="container">
      <div class="row">
        <div class="col-lg-6 offset-lg-3">
          <div class="section-heading  wow bounceIn" data-wow-duration="1s" data-wow-delay="0.2s">
            <h2>Click the <em>Icon</em> below <span> to navigate to each function</span></h2>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-4 col-sm-6">
          <a href="#" onclick='$("#department").trigger("click");'>
            <div class="item wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.3s">
              <div class="hidden-content">
                <h4>Department Record</h4>
                <p>Click to perform action on employee table</p>
              </div>
              <div class="showed-content">
                <img src="assets/images/department.png" alt="">
              </div>
            </div>
          </a>
        </div>
        <div class="col-lg-4 col-sm-6">
          <a href="#" onclick='$("#department_employee").trigger("click");'>
            <div class="item wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.4s">
              <div class="hidden-content">
                <h4>Department-Employee Record</h4>
                <p>Click to perform action on department-employee table</p>
              </div>
              <div class="showed-content">
                <img src="assets/images/departmentemployee.png" alt="">
              </div>
            </div>
          </a>
        </div>
        <div class="col-lg-4 col-sm-6">
          <a href="#" onclick='$("#employee").trigger("click");'>
            <div class="item wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.5s">
              <div class="hidden-content">
                <h4>Employee Record</h4>
                <p>Click to perform action to employee table.</p>
              </div>
              <div class="showed-content">
                <img src="assets/images/employee.png" alt="">
              </div>
            </div>
          </a>
        </div>
        
      </div>
    </div>
  </div>
  
  <div id="contact" class="contact-us section">
    <div class="container">
      <div class="row">
        <div class="col-lg-6 align-self-center wow fadeInLeft" data-wow-duration="0.5s" data-wow-delay="0.25s">
          <div class="section-heading">
            <h2>Feel Free To Send Me A Message When You Need</h2>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This web application proposes the development of an online employee department registration system using Servlet, Java Server Page (JSP), Session Bean technologies, and relational database. This web application incorporates object-oriented principles into a 3-tier architecture.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The main function of this system is to allow administrators to register employee details and assign them into different departments. This system is used to track all the employee and department relation records.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beside the create, read, update, and delete (CRUD) function, this system integrates several functionalities such as searching, sorting, pagination to improve user experience. The result can be tabulated according to the actual need.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Moreover, the generate report function is also provided to the administrator for managing purposes.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last but not least, an application-level analysis is carried out in the system. The UML diagrams which indicate the overall view of the system design are illustrated in this poster.</p>
            <div class="phone-info">
              <h4>For any enquiry, Call:<span><i class="fa fa-phone"></i> <a href="#">+6011-38100852</a></span></h4>
            </div>
          </div>
        </div>
        <div class="col-lg-6 wow fadeInRight" data-wow-duration="0.5s" data-wow-delay="0.25s">
          <form id="contact" action="" method="post">
            <div class="row">
              <div class="col-lg-6">
                <fieldset>
                  <input type="name" name="name" id="name" placeholder="Name" autocomplete="on" required>
                </fieldset>
              </div>
              <div class="col-lg-6">
                <fieldset>
                  <input type="surname" name="surname" id="surname" placeholder="Surname" autocomplete="on" required>
                </fieldset>
              </div>
              <div class="col-lg-12">
                <fieldset>
                  <input type="text" name="email" id="email" pattern="[^ @]*@[^ @]*" placeholder="Your Email" required="">
                </fieldset>
              </div>
              <div class="col-lg-12">
                <fieldset>
                  <textarea name="message" type="text" class="form-control" id="message" placeholder="Message" required=""></textarea>  
                </fieldset>
              </div>
              <div class="col-lg-12">
                <fieldset>
                  <button type="submit" id="form-submit" class="main-button ">Send Message</button>
                </fieldset>
              </div>
            </div>
            <div class="contact-dec">
              <img src="assets/images/contact-decoration.png" alt="">
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
<%@ include file="footer.jsp" %>  