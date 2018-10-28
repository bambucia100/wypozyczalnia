package szbd.projekt.projektbazy.rentalOffice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RentalOfficeController {

	@Autowired
	private RentalOfficeService rentalOfficeService;
	@Autowired RentalOfficeRepository rentalOfficeRepository;
	
	@RequestMapping(method=RequestMethod.GET,value="/rental officies")
	public List<RentalOffice> getAllRentalOfficies() {
		return rentalOfficeService.getAllRentalOffices();
	}
	
	@RequestMapping("/rental officies/{idRentalOffice}")
	public Optional<RentalOffice> getRentalOfficies(@PathVariable String idRentalOffice)
	{
		return rentalOfficeService.getRentalOffice(idRentalOffice);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/rentalOffice")
	public void addRentalOffice(@RequestBody RentalOffice rentalOffice)
	{
		 rentalOfficeService.addRentalOffice(rentalOffice);
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE,value="/rentalOffices/{idRentalOffice}")
	public void deleteRentalOffice(@PathVariable String idRentalOffice)
	{
		 rentalOfficeService.deleteRentalOffice(idRentalOffice);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/rentalOfficies/{idRentalOffice}")
	public void updateBeer(@RequestBody RentalOffice rentalOffice,@PathVariable String idRentalOffice)
	{
		 rentalOfficeService.updateRentalOffice(idRentalOffice, rentalOffice);
	}

}
