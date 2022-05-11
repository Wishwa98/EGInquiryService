$(document).ready(function()
{

	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
	
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
// Form validation-------------------
	var status = validateInquiryForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
// If valid------------------------
	var type = ($("#hidInquiryIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
			{
				url : "InquriesAPI",
				type : type,
				data : $("#formInquiry").serialize(),
				dataType : "text",
				complete : function(response, status)
				{
					onInquirySaveComplete(response.responseText, status);
				}
			});
	
});


function onInquirySaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divInquriesGrid").html(resultSet.data);
			
		} else if (resultSet.status.trim() == "error")
		{
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
			
		}
	} else if (status == "error")
	{
		
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	
	} else
	{
		
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	
	}
	
	$("#hidInquiryIDSave").val("");
	$("#formInquiry")[0].reset();

}

$(document).on("click", ".btnRemove", function(event)
{
		 $.ajax(
		 {
			 url : "InquriesAPI",
			 type : "DELETE",
			 data : "inquiryID=" + $(this).data("inquiryid"),
			 dataType : "text",
			 complete : function(response, status)
			 {
				 
				 onInquiryDeleteComplete(response.responseText, status);
			 
			 }
		 
		 });
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidInquiryIDSave").val($(this).data("inquiryid")); 
	$("#InquiryTitle").val($(this).closest("tr").find('td:eq(0)').text());
	$("#InquiryDesc").val($(this).closest("tr").find('td:eq(1)').text());
	$("#ContactNum").val($(this).closest("tr").find('td:eq(2)').text());
	
});


function onInquiryDeleteComplete(response, status)
{
	if (status == "success")
	{
		
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		
		{
			
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divInquriesGrid").html(resultSet.data);
		
		} else if (resultSet.status.trim() == "error")
		{
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		
		}
	} else if (status == "error")
	{
		
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	
	} else
	{
		
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	
	}
}


// CLIENTMODEL=========================================================================
function validateInquiryForm()
{
	// Inquiry Title-------------------------
	if ($("#InquiryTitle").val().trim() == "")
	{
		return "Insert Inquiry Title.";
	}
	// Inquiry Description---------------------------
	if ($("#InquiryDesc").val().trim() == "")
	{
		return "Insert Inquiry Description";
	} 
	//insert contact num
		if ($("#ContactNum").val().trim() == "")
	{
		return "Insert Contact Number";
	} 
	// is numerical value
	var tmpContact = $("#ContactNum").val().trim();
	if (!$.isNumeric(tmpContact))
	 {
	 return "Insert a Number for the Contact Number";
	 }
	
	return true;
}