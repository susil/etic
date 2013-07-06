<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:url value="/systems/records" var="recordsUrl"/>
<c:url value="/systems/create" var="addUrl"/>
<c:url value="/systems/update" var="editUrl"/>
<c:url value="/systems/delete" var="deleteUrl"/>
<c:url value="/systems/connect" var="connectUrl"/>
<c:url value="/systems/consumers" var="consumersUrl"/>
<c:url value="/systems/record/" var="aRecordUrl"/>
<c:url value="/systems/producers" var="producersUrl"/>

<html>
<head>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/tdata_page.css"/>'/>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/tdata_table.css"/>'/>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>

 	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/jquery-ui-1.10.3.custom.css"/>'/>

	<script type='text/javascript' src='<c:url value="/resources/js/jquery-2.0.2.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.dataTables.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
	
	<!-- Place jquery UI need to go after JQuerry call -->
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.10.3.custom.js"/>'></script>
	
	
	<script type='text/javascript'>
	
	
	
	
	$(function() {
		
		 $( "#tabs" ).tabs();
		 
  	    $( "#newLaunchdate" ).datepicker({
	    	autoSize: true,
	        showOn: "button",
	        buttonImage: "resources/images/calendar.gif",
	        buttonImageOnly: true
	      });  
  	    
  	    $( "#editLaunchdate" ).datepicker({
	    	autoSize: true,
	        showOn: "button",
	        buttonImage: "resources/images/calendar.gif",
	        buttonImageOnly: true
	      });  
  	    
  	  
	    $( "#editCurrentreleaseddate" ).datepicker({
	    	autoSize: true,
	        showOn: "button",
	        buttonImage: "resources/images/calendar.gif",
	        buttonImageOnly: true
	      });  	  
	    $( "#newCurrentreleaseddate" ).datepicker({
	    	autoSize: true,
	        showOn: "button",
	        buttonImage: "resources/images/calendar.gif",
	        buttonImageOnly: true
	      });  	  
  	    
	    //http://localhost:8080/myapp/images/calendar.gif
	    //http://localhost:8080/myapp/images/calendar.gif
	    
		// init
		urlHolder.records = '${recordsUrl}';
		urlHolder.add = '${addUrl}';
		urlHolder.edit = '${editUrl}';
		urlHolder.del = '${deleteUrl}';
		urlHolder.connect = '${connectUrl}';
		urlHolder.consumers ='${consumersUrl}';
		urlHolder.aRecord ='${aRecordUrl}';
		urlHolder.producers ='${producersUrl}';
		
		loadTable();
		
		$('#newBtn').click(function() { 
			toggleForms('new');
			toggleCrudButtons('hide');
		});
		
		$('#editBtn').click(function() { 
			if (hasSelected()) {
				toggleForms('edit');
				toggleCrudButtons('hide');
				fillEditForm();
			}
		});
		
		$('#reloadBtn').click(function() { 
			loadTable();
		});

		$('#deleteBtn').click(function() {
			if (hasSelected()) { 
				submitDeleteRecord();
			}
		});
		
		$('#newForm').submit(function() {
			event.preventDefault();
			//alert('1');
			submitNewRecord();
		});
		
		$('#editForm').submit(function() {
			event.preventDefault();
			submitUpdateRecord();
		});

		$('#closeNewForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
		
		$('#closeEditForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});

		
		$('#connectBtn').click(function() { 
			if (hasSelected()) {
				toggleForms('connect');
				toggleCrudButtons('hide');
				fillConnectForm();
			}
		});
		
		$('#connectForm').submit(function() {
			event.preventDefault();
			submitConnectRecord();
		});
		
		$('#closeConnectForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
	
		$('#consumerBtn').click(function() { 
			//alert('consumerBtn...')
			if (hasSelected()) {
				//alert('loadConsumerTable...')
				
				toggleForms('consumer');
				//toggleCrudButtons('hide');
				loadConsumerTable();
			}
		});	

		
		$('#producerBtn').click(function() { 
			//alert('consumerBtn...')
			if (hasSelected()) {
				//alert('loadConsumerTable...')
				
				toggleForms('producer');
				//toggleCrudButtons('hide');
				loadProducerTable();
			}
		});	
		
	});
/* 	
    var jsonList = {"Table" : [{"id" : "2","title" : "MPM21"},
                               {"id" : "3","title" : "MPM22"},
                               {"id" : "4","title" : "MPM23"},
                                {"id" : "5","title" : "MPM42"},
                                {"id" : "6","title" : "MPM52"}]}; */
    
/*                             	jQuery.fn.dataTableExt.oSort['date-us-asc']  = function(a,b) {  
                            	    //use text()
                            	    var ukDatea = $(a).text().split('/');
                            	    var ukDateb = $(b).text().split('/');

                            	    var x = (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
                            	    var y = (ukDateb[2] + ukDateb[1] + ukDateb[0]) * 1;

                            	    return ((x < y) ? -1 : ((x > y) ?  1 : 0));
                            	};

                            	jQuery.fn.dataTableExt.oSort['date-us-desc'] = function(a,b) {
                            	    //use text()
                            	    var ukDatea = $(a).text().split('/');
                            	    var ukDateb = $(b).text().split('/');

                            	    var x = (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
                            	    var y = (ukDateb[2] + ukDateb[1] + ukDateb[0]) * 1;

                            	    return ((x < y) ? 1 : ((x > y) ?  -1 : 0));
                            	}    */                         	
 
                            	
/*            	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
           		   "date-us-pre": function ( a ) {
           		       var b = a.match(/(\d{1,2})\/(\d{1,2})\/(\d{2,4})/),
           		           month = b[1],
           		           day = b[2],
           		           year = b[3];
           		 
           		 
           		       if(year.length == 2){
           		           if(parseInt(year, 10)<70) year = '20'+year;
           		           else year = '19'+year;
           		       }
           		       if(month.length == 1) month = '0'+month;
           		       if(day.length == 1) day = '0'+day;
           		 
           		       var tt = year+month+day;
           		       return  tt;
           		   },
           		   "date-us-asc": function ( a, b ) {
           		       return a - b;
           		   },
           		 
           		   "date-us-desc": function ( a, b ) {
           		       return b - a;
           		   }
           		});
           		 
           		jQuery.fn.dataTableExt.aTypes.unshift(
           		   function ( sData )
           		   {
           		       if (sData !== null && sData.match(/\d{1,2}\/\d{1,2}\/\d{2,4}/))
           		       {
           		 
           		           return 'date-us';
           		       }
           		       return null;
           		   }
           		);   */                          	
                            	
</script>

	<title>User Records</title>
	
		<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				//alert('lets call ajax to get data...');
				//var 
				var  oTable = $('#tableSystems').dataTable( {
					"bProcessing": true,
					"sAjaxSource": '/myapp/systems/systemtable',
					"bDestroy": true,
					"bAutoWidth" : false, 
					 "aoColumns":[
						{"sWidth": '5%', "mDataProp": "id", bSearchable: false, bSortable: false},
						{"sWidth": '20%', "mDataProp": "title"},
						{"sWidth": '30%', "mDataProp": "description"},
						{"sWidth": '10%', "mDataProp": "techcontact"},
						{"sWidth": '10%', "mDataProp": "funccontact"},
						{"sWidth": '10%', "mDataProp": "launchdate", "sType": "date-us"
							,    
							"mRender": function ( data, type, full ) {
							var javascriptDate = new Date(data);
							var monthInt = javascriptDate.getMonth() + 1;
							  javascriptDate = monthInt+"/"+javascriptDate.getDate()+"/"+javascriptDate.getFullYear();
							return "<div class= date>"+javascriptDate+"<div>";
				              } 							
						
						},
						{"sWidth": '5%', "mDataProp": "currentrelease"},
						{"sWidth": '10%', "mDataProp": "currentreleaseddate"
							,    
							"mRender": function ( data, type, full ) {
							var javascriptDate = new Date(data);
							var monthInt = javascriptDate.getMonth() + 1;
							  javascriptDate = monthInt+"/"+javascriptDate.getDate()+"/"+javascriptDate.getFullYear();
							return "<div class= date>"+javascriptDate+"<div>";
							}

						}
					    						
						
						
						],
						"aoColumnDefs": [ {
						      "aTargets": [ 0 ],
						      //"mData": "download_link",
						      "mRender": function ( data, type, full ) {
						        //return '<a href="'+data+'">Download</a>';
					        return '<input type="radio" name="index" id="index" value="'+data+'"/>' +
					        '<input type="hidden" name="id" id="id" value="' + data + '"/>';
						      }
						    } ]	
				} );
				//alert(oTable);
				
				
			} );
		</script>
</head>

<body>

<div id="container">
	<hr/>
	<div id="dynamic" style="width:100%">
	<div id="dt_tableSystems" class="ex_highlight_row">
	<table id='tableSystems' class="display" >
		<caption></caption>
		<thead>
			<tr>
			<th width="5%">Select</th>
			<th width="20%">Title</th>
			<th width="30%">Description</th>
		    <th width="10%">Technical Contact</th>
		    <th width="10%">Functional Contact</th>
		    <th width="10%">Launch Date</th>
		    <th width="5%">Current Release</th>
		    <th width="10%">Current Release Date</th>			
			
			</tr>
		</thead>
	<tbody>
		
	</tbody>
	<tfoot>
<!-- 		<tr> -->
<!-- 			<th>Id</th> -->
<!-- 			<th>Title</th> -->
<!-- 			<th>Description</th> -->
<!-- 		</tr> -->
	</tfoot>
	
	</table>
	</div>
	</div>
	
</div>	


	<div id="divConsumers">
		<table id='tableConsumers'>
			<caption></caption>
			<thead>
				<tr>
					<th></th>
					<th>Producer</th>
					<th>Consumer</th>
					<th>Dataset</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="divProducers">
		<table id='tableProducers'>
			<caption></caption>
			<thead>
				<tr>
					<th></th>
					<th>Consumer</th>
					<th>Producer</th>
					<th>Dataset</th>
				</tr>
			</thead>
		</table>
	</div>

	<div id='controlBar1'>
	</div>

	<div id='controlBar'>
	<br><br>
		<input type='button' value='New' id='newBtn' />
		<input type='button' value='Delete' id='deleteBtn' />
		<input type='button' value='Edit' id='editBtn' />
		<input type='button' value='Reload' id='reloadBtn' />
		<input type='button' value='Connect' id='connectBtn' />
		<input type='button' value='Produces to' id='consumerBtn' />
		<input type='button' value='Consumes from' id='producerBtn' />
	</div>
	
	<div id='newForm'>
		<form>
  			<fieldset>
				<legend>New</legend>
				<label for='newId'>Id </label><input type='text' id='newId'/><br/>
				<label for='newTitle'>Title </label><input type='text' id='newTitle'/><br/>
				<label for='newDescription'>Description </label><input type='text' id='newDescription'/><br/>
				<label for='newTechcontact'>Technical Contact </label><input type='text' id='newTechcontact'/><br/>
				<label for='newFunccontact'>Functional Contact </label><input type='text' id='newFunccontact'/><br/>
				<label for='newLaunchdate'>Launch Date </label><input type='text' id='newLaunchdate'/><br/>
				<label for='newCurrentrelease'>Current Release </label><input type='text' id='newCurrentrelease'/><br/>
				<label for='newCurrentreleaseddate'>Current Release Date </label><input type='text' id='newCurrentreleaseddate'/><br/>
				
				
  			</fieldset>
			<input type='button' value='Close' id='closeNewForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	
	<div id='editForm'>
		<form> 
  			<fieldset>
				<legend>Edit Record</legend>
				<input type='hidden' id='editUsername'/>
				<label for='editId'>Id</label><input type='text' id='editId'/><br/>
				<label for='editTitle'>Title</label><input type='text' id='editTitle'/><br/>
				<label for='editDescription'>Description</label><input type='text' id='editDescription'/><br/>

				<label for='editTechcontact'>Technical Contact</label><input type='text' id='editTechcontact'/><br/>
				<label for='editFunccontact'>Functional Contact</label><input type='text' id='editFunccontact'/><br/>
				<label for='editLaunchdate'>Launch Date</label><input type='text' id='editLaunchdate'/><br/>
				<label for='editCurrentrelease'>Current Release</label><input type='text' id='editCurrentrelease'/><br/>
				<label for='editCurrentreleaseddate'>Current Release Date</label><input type='text' id='editCurrentreleaseddate'/><br/>

			</fieldset>	
			<input type='button' value='Close' id='closeEditForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>

	<div id='connectForm'>
		<form> 
  			<fieldset>
				<legend>Connect System</legend>
				<input type='hidden' id='editUsername'/>
				<label for='sourceId'>Source</label><input type='text' id='sourceId'/><br/>
				<label for='destinationId'>Destination</label>
				<select id="destinationId"></select><br/>
				<label for='datasetname'>DataSet</label><input type='text' id='datasetname'/><br/>
			</fieldset>	
			<input type='button' value='Close' id='closeConnectForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	


</body>
</html>