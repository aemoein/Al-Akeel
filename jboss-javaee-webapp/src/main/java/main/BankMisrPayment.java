package main;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/bankmisrpayment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankMisrPayment {
	public BankMisrPayment() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
    @Path("/validate-credit-card/{creditCardNumber}")
	 public Response validateCreditCard(@PathParam("creditCardNumber") String creditCardNumber) {
        if (creditCardNumber.length() == 16 && creditCardNumber.matches("[1-9][0-9]*")) {
            return Response.ok("Credit card number is valid.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Credit card number is invalid.").build();
        }
    }
	
	@GET
    @Path("/validate-cvv/{cvv}")
    public Response validateCVV(@PathParam("cvv") String cvv) {
        if (cvv.length() == 3 && cvv.matches("[0-9]{3}")) {
            return Response.ok("CVV is valid.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("CVV is invalid.").build();
        }
    }

	@GET
    @Path("/validate-year/{month}/{year}")
    public Response validateYear(@PathParam("month") int month, @PathParam("year") int year) {
        if (checkLength(month, year)) {
            if (month < 13 && month > 0 && year >= 23) {
                if (year == 23 && month < 5) {
                    return Response.ok("Year or month are invalid.").build();
                }
                return Response.ok("Year and month are valid.").build();
            } else {
                return Response.ok("Year or month are invalid.").build();
            }
        }
        return Response.ok("Year or month are invalid.").build();
    }
	
	
	public boolean checkLength(int month, int year) {
		String yearString = String.valueOf(year);
	    String monthString = String.valueOf(month);

	    if (yearString.length() == 2 && (monthString.length() == 1 || monthString.length() == 2)) {
	        System.out.println("Year and month are valid.");
	        return true;
	    } else {
	        System.out.println("Year or month are invalid.");
	        return false;
	    }
	}
	

}
