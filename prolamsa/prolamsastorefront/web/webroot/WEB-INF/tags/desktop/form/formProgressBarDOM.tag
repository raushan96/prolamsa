<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="value" required="false" type="java.lang.Double" %>
<%@ attribute name="lowLimit" required="false" type="java.lang.Integer" %>
<%@ attribute name="highLimit" required="false" type="java.lang.Integer" %>

  
  <style>	  
 .ui-progressbar.back-green .ui-progressbar-value { 
    background: #2FD90D; 
    border : 1px solid #DDDDDD;
    color : #333333;
    font-weight : bold;
 }	
 
 .ui-progressbar.back-yellow .ui-progressbar-value { 
    background: #DDF00C; 
    border : 1px solid #DDDDDD;
    color : #333333;
    font-weight : bold;
 }	
 
 .ui-progressbar.back-red .ui-progressbar-value { 
    background: #FF3333; 
    border : 1px solid #DDDDDD;
    color : #333333;
    font-weight : bold;
 }
 </style>
 

<script type="text/javascript">

onDOMLoaded(loadProgressBar);

function loadProgressBar()
{
	
  var perValue = (${value} > 0) ? Math.round(${value}) : 0;
  var perValue = (perValue > 100) ? 100 : perValue;
  
 
                      
     $( "#progressbar-score-card" ).css("height","1.5em");
     $( "#progressbar-score-card" ).css("overflow","hidden");
     $( "#progressbar-score-card" ).css("text-align","center");     
     $( "#progressbar-score-card" ).css("width","200px"); 
                      
     $( "#progress-label" ).css("position","absolute");      
     $( "#progress-label" ).css("height","1em");       
     $( "#progress-label" ).css("font-size","12px");
     $( "#progress-label" ).css("font-weight","bold");
     $( "#progress-label" ).css("vertical-align","middle");
     $( "#progress-label" ).css("horizontal-align","middle");
     $( "#progress-label" ).css("width","200px");                  
                                    
      $(function() {
         $( "#progressbar-score-card" ).progressbar({
          value: perValue
         }).removeClass("back-green back-yellow back-red")
         .addClass(perValue < ${lowLimit} ? "back-green" : perValue < ${highLimit} ? "back-yellow" : "back-red");
	$( "#progress-label" ).text( $( "#progressbar-score-card" ).progressbar("value") + " %" );
      });  
}

</script>

 <div id="progressbar-score-card" >
	  <div id="progress-label"></div>	 
</div> 

